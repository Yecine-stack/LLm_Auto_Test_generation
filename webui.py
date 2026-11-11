import json
import urllib.request
from pathlib import Path
import threading
import uuid
import shutil

from generate_tests import generate_test_project
from flask import Flask, render_template, request, send_file, redirect, url_for

import converter
from json_to_csv import convert_json_to_csv

app = Flask(__name__)

UPLOAD_DIR = Path("uploads")
OUTPUT_DIR = Path("outputs")

UPLOAD_DIR.mkdir(exist_ok=True)
OUTPUT_DIR.mkdir(exist_ok=True)

PROMPT_PATH = Path("templates/extraction_prompt.txt")

ALLOWED_SUFFIX = ".txt"

# Same settings as generate_tests.py
MODEL = "qwen_7b_fullGPU:latest"
OLLAMA_URL = "http://localhost:11434/api/chat"

TEMPERATURE = 0.2
NUM_CTX = 9216
NUM_PREDICT = 4096
TIMEOUT = 600

JOBS = {}


def run_generation_job(job_id, backlog_path, spec_path, output_dir, limit):
    job = JOBS[job_id]

    def on_progress(index, total, story_id, status, error):
        job["done"] = index
        job["total"] = total
        job["last_story"] = story_id
        job["last_status"] = status
        if status == "failed":
            job["failed"].append({"story_id": story_id, "error": error})

    try:
        result = generate_test_project(
            backlog_path,
            spec_path,
            output_dir,
            limit=limit,
            progress_callback=on_progress,
        )
        job["status"] = "finished"
        job["successful"] = len(result["successful"])
        job["failed_count"] = len(result["failed"])
        job["zip_path"] = make_zip(output_dir, job_id)
    except Exception as error:
        job["status"] = "error"
        job["error"] = str(error)


def make_zip(output_dir, job_id):
    base = Path("outputs") / f"tests_{job_id}"
    archive = shutil.make_archive(str(base), "zip", root_dir=output_dir)
    return archive

def list_uploaded():
    return sorted(p.name for p in UPLOAD_DIR.glob(f"*{ALLOWED_SUFFIX}"))


def build_extraction_prompt(specification):
    template = PROMPT_PATH.read_text(encoding="utf-8")
    return template.replace("{specification}", specification)


def ask_ollama(prompt):
    payload = {
        "model": MODEL,
        "messages": [{"role": "user", "content": prompt}],
        "stream": False,
        "options": {
            "temperature": TEMPERATURE,
            "num_ctx": NUM_CTX,
            "num_predict": NUM_PREDICT,
        },
    }

    request_obj = urllib.request.Request(
        OLLAMA_URL,
        data=json.dumps(payload).encode("utf-8"),
        headers={"Content-Type": "application/json"},
        method="POST",
    )

    with urllib.request.urlopen(request_obj, timeout=TIMEOUT) as response:
        result = json.loads(response.read().decode("utf-8"))

    content = result.get("message", {}).get("content", "")

    if not content.strip():
        raise ValueError("Empty response from Ollama.")

    return content


@app.route("/")
def index():
    return render_template("index.html", files=list_uploaded())


@app.route("/upload", methods=["POST"])
def upload():
    uploaded = request.files.get("spec_file")

    if uploaded is None or uploaded.filename == "":
        return render_template(
            "index.html", files=list_uploaded(), error="No file selected."
        )

    filename = Path(uploaded.filename).name

    if Path(filename).suffix.lower() != ALLOWED_SUFFIX:
        return render_template(
            "index.html",
            files=list_uploaded(),
            error="Only .txt files are accepted.",
        )

    destination = UPLOAD_DIR / filename
    uploaded.save(destination)

    content = destination.read_text(encoding="utf-8", errors="replace")

    return render_template(
        "index.html",
        files=list_uploaded(),
        uploaded_name=filename,
        uploaded_content=content,
    )


@app.route("/extract", methods=["POST"])
def extract():
    filename = request.form.get("filename", "")

    if not filename:
        return render_template(
            "index.html", files=list_uploaded(), error="No file selected."
        )

    safe_name = Path(filename).name
    source = UPLOAD_DIR / safe_name

    if not source.exists():
        return render_template(
            "index.html", files=list_uploaded(), error="File not found."
        )

    specification = source.read_text(encoding="utf-8", errors="replace")

    stem = Path(safe_name).stem

    try:
        prompt = build_extraction_prompt(specification)
        response = ask_ollama(prompt)
    except Exception as error:
        return render_template(
            "index.html",
            files=list_uploaded(),
            uploaded_name=safe_name,
            uploaded_content=specification,
            error=f"Extraction failed: {error}",
        )

    try:
        backlog = converter.convert_to_json(response)
    except Exception as error:
        return render_template(
            "index.html",
            files=list_uploaded(),
            uploaded_name=safe_name,
            uploaded_content=specification,
            extraction_result=response,
            error=f"JSON conversion failed: {error}",
        )

    json_path = OUTPUT_DIR / f"{stem}.json"
    json_path.write_text(
        json.dumps(backlog, indent=4, ensure_ascii=False),
        encoding="utf-8",
    )

    csv_path = OUTPUT_DIR / "backlog.csv"

    try:
        convert_json_to_csv(str(json_path), str(csv_path))
    except Exception as error:
        return render_template(
            "index.html",
            files=list_uploaded(),
            uploaded_name=safe_name,
            uploaded_content=specification,
            extraction_result=response,
            json_text=json.dumps(backlog, indent=4, ensure_ascii=False),
            error=f"CSV conversion failed: {error}",
        )

    epic_count = len(backlog["epics"])
    story_count = sum(len(e["user_stories"]) for e in backlog["epics"])

    return render_template(
        "index.html",
        files=list_uploaded(),
        uploaded_name=safe_name,
        uploaded_content=specification,
        extraction_result=response,
        json_text=json.dumps(backlog, indent=4, ensure_ascii=False),
        csv_filename="backlog.csv",
        epic_count=epic_count,
        story_count=story_count,
    )


@app.route("/download/<path:filename>")
def download(filename):
    safe_name = Path(filename).name
    target = OUTPUT_DIR / safe_name

    if not target.exists():
        return render_template(
            "index.html", files=list_uploaded(), error="File not found."
        )

    return send_file(target, as_attachment=True)

@app.route("/generate", methods=["POST"])
def generate():
    filename = request.form.get("filename", "")
    limit_raw = request.form.get("limit", "").strip()

    if not filename:
        return render_template("index.html", files=list_uploaded(),
                               error="No file selected.")

    stem = Path(filename).stem
    backlog_path = OUTPUT_DIR / f"{stem}.json"
    spec_path = UPLOAD_DIR / Path(filename).name

    if not backlog_path.exists():
        return render_template("index.html", files=list_uploaded(),
                               error="Generate the backlog first.")

    if not spec_path.exists():
        return render_template("index.html", files=list_uploaded(),
                               error="Specification file not found.")

    limit = None
    if limit_raw:
        try:
            limit = int(limit_raw)
            if limit < 1:
                raise ValueError
        except ValueError:
            return render_template("index.html", files=list_uploaded(),
                                   error="Limit must be a positive integer.")

    job_id = uuid.uuid4().hex
    output_dir = OUTPUT_DIR / f"{stem}_tests"
    output_dir.mkdir(parents=True, exist_ok=True)

    JOBS[job_id] = {
        "status": "running",
        "done": 0,
        "total": 0,
        "last_story": None,
        "last_status": None,
        "failed": [],
        "successful": 0,
        "failed_count": 0,
        "zip_path": None,
        "error": None,
        "stem": stem,
    }

    thread = threading.Thread(
        target=run_generation_job,
        args=(job_id, str(backlog_path), str(spec_path),
              output_dir, limit),
        daemon=True,
    )
    thread.start()

    return redirect(url_for("job_status_page", job_id=job_id))


@app.route("/job/<job_id>")
def job_status_page(job_id):
    if job_id not in JOBS:
        return render_template("index.html", files=list_uploaded(),
                               error="Unknown job.")
    return render_template("job.html", job_id=job_id, job=JOBS[job_id])


@app.route("/job/<job_id>/status")
def job_status(job_id):
    job = JOBS.get(job_id)
    if job is None:
        return {"error": "unknown job"}, 404
    return {
        "status": job["status"],
        "done": job["done"],
        "total": job["total"],
        "last_story": job["last_story"],
        "last_status": job["last_status"],
        "failed": job["failed"],
        "successful": job["successful"],
        "failed_count": job["failed_count"],
        "zip_url": (url_for("download", filename=Path(job["zip_path"]).name)
                    if job["zip_path"] else None),
        "error": job["error"],
    }
if __name__ == "__main__":
    app.run(debug=True)