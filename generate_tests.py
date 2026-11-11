import argparse
import json
import re
import sys
import urllib.request
from pathlib import Path


# ============================================================
# CONFIGURATION
# ============================================================

MODEL = "qwen_7b_fullGPU:latest"
OLLAMA_URL = "http://localhost:11434/api/chat"

TEMPERATURE = 0.2
NUM_CTX = 9216
NUM_PREDICT = 4096
TIMEOUT = 600

DEFAULT_OUTPUT_DIR = Path(
    "test_project/src/test/java/generated"
)

DEBUG_DIR = Path("generation_debug")

DEFAULT_IMPORTS = [
    "import org.junit.jupiter.api.Test;",
    "import static org.junit.jupiter.api.Assertions.*;",
]


# ============================================================
# INPUT
# ============================================================

def load_backlog(path):
    with open(path, "r", encoding="utf-8") as file:
        backlog = json.load(file)

    epics = backlog.get("epics")
    if not isinstance(epics, list):
        raise ValueError("JSON must contain an 'epics' list.")

    stories = []

    for epic in epics:
        if not isinstance(epic, dict):
            continue

        for story in epic.get("user_stories", []):
            if isinstance(story, dict):
                stories.append((epic, story))

    if not stories:
        raise ValueError("No user stories found.")

    return stories


def load_specification(path):
    return Path(path).read_text(encoding="utf-8")


# ============================================================
# PROMPT
# ============================================================

def build_prompt(specification, epic, story):
    story_id = story.get("id", "US")

    return f"""
You are an experienced Java software testing engineer.

Design JUnit 5 tests based ONLY on the requirements below.
The Java application does not exist yet. Generate tests from the
requirements; do not ask for or require implementation source code.

GENERAL CAHIER DES CHARGES:
{specification}

PARENT EPIC:
ID: {epic.get("id", "")}
TITLE: {epic.get("title", "")}

USER STORY:
ID: {story_id}
TITLE: {story.get("title", "")}
DESCRIPTION: {story.get("description", "")}

REQUIREMENTS FOR YOUR OUTPUT:

1. Generate real, compilable JUnit 5 test code.
2. Generate tests only. Do not generate production application code.
3. Infer reasonable Java classes, methods and interfaces from the
   requirements when necessary. The application will be implemented later.
4. Add short Java comments when an assumption about the future API is needed.
5. Test observable behavior, not private implementation details.
6. Cover relevant normal cases, invalid inputs and boundary conditions.
7. Do not invent behavior that is not supported by the requirements.
8. Test only this user story. Do not test other stories.
9. Generate as many focused test methods as this story reasonably needs.
10. Every method annotated as a test must have a name beginning with
    "{story_id}_".
11. Use descriptive method names.
12. Use JUnit 5 annotations and assertions.
13. Do not use @Disabled.
14. You MAY return the test methods alone OR wrap them in one Java class.
15. If you return a class, it must contain the test methods directly.
    Do not generate nested classes or multiple top-level classes.
16. You may include required Java import statements.
17. Do not include a package declaration.
18. Do not use Markdown code fences.
19. Do not include explanations outside the Java code.

Return ONLY Java code.
"""


# ============================================================
# OLLAMA
# ============================================================

def ask_ollama(prompt):
    payload = {
        "model": MODEL,
        "messages": [
            {
                "role": "user",
                "content": prompt,
            }
        ],
        "stream": False,
        "options": {
            "temperature": TEMPERATURE,
            "num_ctx": NUM_CTX,
            "num_predict": NUM_PREDICT,
        },
    }

    request = urllib.request.Request(
        OLLAMA_URL,
        data=json.dumps(payload).encode("utf-8"),
        headers={"Content-Type": "application/json"},
        method="POST",
    )

    with urllib.request.urlopen(request, timeout=TIMEOUT) as response:
        result = json.loads(response.read().decode("utf-8"))

    content = result.get("message", {}).get("content", "")

    if not content.strip():
        raise ValueError("Empty response from Ollama.")

    return content


# ============================================================
# JAVA RESPONSE EXTRACTION
# ============================================================

def remove_markdown_fences(response):
    response = response.strip()

    match = re.search(
        r"```(?:java)?\s*(.*?)```",
        response,
        re.DOTALL | re.IGNORECASE,
    )

    if match:
        return match.group(1).strip()

    return response


def find_matching_brace(source, opening_index):
    """Find the matching closing brace while ignoring Java comments and strings."""
    depth = 0
    state = "normal"
    i = opening_index

    while i < len(source):
        char = source[i]
        next_char = source[i + 1] if i + 1 < len(source) else ""

        if state == "line_comment":
            if char == "\n":
                state = "normal"

        elif state == "block_comment":
            if char == "*" and next_char == "/":
                state = "normal"
                i += 1

        elif state == "string":
            if char == "\\":
                i += 1
            elif char == '"':
                state = "normal"

        elif state == "text_block":
            if source.startswith('"""', i):
                state = "normal"
                i += 2

        elif state == "char":
            if char == "\\":
                i += 1
            elif char == "'":
                state = "normal"

        else:
            if char == "/" and next_char == "/":
                state = "line_comment"
                i += 1
            elif char == "/" and next_char == "*":
                state = "block_comment"
                i += 1
            elif source.startswith('"""', i):
                state = "text_block"
                i += 2
            elif char == '"':
                state = "string"
            elif char == "'":
                state = "char"
            elif char == "{":
                depth += 1
            elif char == "}":
                depth -= 1
                if depth == 0:
                    return i

        i += 1

    raise ValueError("Could not find the closing brace of the generated Java class.")


def extract_java_code(response):
    """
    Return (imports, test_code).

    The model may return methods directly or wrap them in one class.
    Package declarations are discarded because the output file owns its package.
    """
    code = remove_markdown_fences(response)

    imports = re.findall(
        r"^\s*import\s+(?:static\s+)?[\w.*]+\s*;\s*$",
        code,
        re.MULTILINE,
    )

    # Remove package and import lines before extracting the class body.
    code = re.sub(
        r"^\s*(?:package\s+[\w.]+\s*;|import\s+(?:static\s+)?[\w.*]+\s*;)\s*$",
        "",
        code,
        flags=re.MULTILINE,
    ).strip()

    class_matches = list(
        re.finditer(
            r"\b(?:public\s+|protected\s+|private\s+)?"
            r"(?:abstract\s+|final\s+)?class\s+\w+"
            r"(?:\s+extends\s+[\w.<>]+)?"
            r"(?:\s+implements\s+[\w.,\s<>]+)?\s*\{",
            code,
        )
    )

    if len(class_matches) > 1:
        raise ValueError("Model returned more than one class declaration.")

    if class_matches:
        match = class_matches[0]
        opening_brace = code.find("{", match.start(), match.end())
        closing_brace = find_matching_brace(code, opening_brace)

        # Reject non-whitespace source after the class. This catches extra
        # top-level declarations instead of silently dropping them.
        trailing = code[closing_brace + 1:].strip()
        if trailing:
            raise ValueError(
                "Unexpected Java code appears after the generated test class."
            )

        code = code[opening_brace + 1:closing_brace].strip()

    return imports, code


# ============================================================
# VALIDATION
# ============================================================

TEST_ANNOTATION_PATTERN = (
    r"@(?:Test|ParameterizedTest|RepeatedTest|TestFactory|TestTemplate)\b"
)


def validate_tests(code, story_id):
    if not code.strip():
        raise ValueError("Empty generated code.")

    if not re.search(TEST_ANNOTATION_PATTERN, code):
        raise ValueError("No JUnit 5 test annotation found.")

    # Match the method declaration immediately following a test annotation.
    test_methods = re.findall(
        TEST_ANNOTATION_PATTERN
        + r"(?:\s*\([^)]*\))?\s*"
        + r"(?:(?:public|protected|private)\s+)?"
        + r"(?:(?:static|final|synchronized)\s+)*"
        + r"void\s+(\w+)\s*\(",
        code,
        flags=re.DOTALL,
    )

    if not test_methods:
        raise ValueError("No JUnit test methods found.")

    for method in test_methods:
        if not method.startswith(story_id + "_"):
            raise ValueError(
                f"Test method '{method}' does not start with {story_id}_."
            )

    return True


# ============================================================
# JAVA TEST FILE (one class per user story)
# ============================================================

def write_story_file(output_dir, story_id, imports, code):
    output_dir.mkdir(parents=True, exist_ok=True)

    all_imports = []
    seen = set()

    for item in DEFAULT_IMPORTS + imports:
        item = item.strip()
        if item and item not in seen:
            seen.add(item)
            all_imports.append(item)

    class_name = f"{story_id}Test"
    file_path = output_dir / f"{class_name}.java"

    with open(file_path, "w", encoding="utf-8", newline="\n") as file:
        file.write("package generated;\n\n")

        for item in all_imports:
            file.write(item + "\n")

        file.write(f"\npublic class {class_name} {{\n\n")
        file.write(code.strip() + "\n\n")
        file.write("}\n")

    return file_path


# ============================================================
# MAIN GENERATION LOOP
# ============================================================

def generate_test_project(
    backlog_path,
    specification_path,
    output_dir,
    limit=None,
    progress_callback=None,
):
    stories = load_backlog(backlog_path)
    specification = load_specification(specification_path)

    if limit is not None:
        if limit < 1:
            raise ValueError("--limit must be at least 1.")
        stories = stories[:limit]

    successful = []
    failed = []

    DEBUG_DIR.mkdir(parents=True, exist_ok=True)

    print("\n" + "=" * 55)
    print("JUNIT TEST GENERATOR")
    print("=" * 55)
    print(f"Model: {MODEL}")
    print(f"Stories: {len(stories)}")
    print(f"Output dir: {output_dir}")

    for index, (epic, story) in enumerate(stories, start=1):
        story_id = story.get("id", "UNKNOWN")
        story_title = story.get("title", "Untitled")

        print(f"\n[{index}/{len(stories)}] {story_id}: {story_title}")

        try:
            prompt = build_prompt(specification, epic, story)
            response = ask_ollama(prompt)

            # Preserve the raw model response for diagnosis if extraction
            # or validation fails.
            raw_path = DEBUG_DIR / f"{story_id}_raw.txt"
            raw_path.write_text(response, encoding="utf-8")

            imports, code = extract_java_code(response)
            validate_tests(code, story_id)

            file_path = write_story_file(output_dir, story_id, imports, code)

            successful.append(
                {
                    "story_id": story_id,
                    "file": str(file_path),
                }
            )

            print(f"SUCCESS: {file_path.name} written.")
            if progress_callback:
                progress_callback(index, len(stories), story_id, "success", None)

        except Exception as error:
            print(f"FAILED: {error}")
            if progress_callback:
                progress_callback(index, len(stories), story_id, "failed", str(error))

            failed.append(
                {
                    "story_id": story_id,
                    "story_title": story_title,
                    "error": str(error),
                }
            )

    error_file = output_dir / "generation_errors.json"
    error_file.parent.mkdir(parents=True, exist_ok=True)

    error_file.write_text(
        json.dumps(failed, indent=4, ensure_ascii=False),
        encoding="utf-8",
    )

    print("\n" + "=" * 55)
    print("GENERATION FINISHED")
    print("=" * 55)
    print(f"Total stories: {len(stories)}")
    print(f"Successful: {len(successful)}")
    print(f"Failed: {len(failed)}")
    print(f"Output dir: {output_dir}")
    print(f"Errors: {error_file}")
    print(f"Raw responses: {DEBUG_DIR.resolve()}")
    return {"successful": successful, "failed": failed}


# ============================================================
# COMMAND LINE
# ============================================================

def main():
    parser = argparse.ArgumentParser(
        description="Generate JUnit 5 tests from a JSON backlog."
    )

    parser.add_argument(
        "backlog",
        help="Path to backlog.json",
    )

    parser.add_argument(
        "--spec",
        required=True,
        help="Path to the general cahier des charges.",
    )

    parser.add_argument(
        "-o",
        "--output-dir",
        type=Path,
        default=DEFAULT_OUTPUT_DIR,
        help="Directory for the generated per-story Java test files.",
    )

    parser.add_argument(
        "--limit",
        type=int,
        default=None,
        help="Generate tests for only the first N stories (useful for a trial run).",
    )

    args = parser.parse_args()

    try:
        generate_test_project(
            args.backlog,
            args.spec,
            args.output_dir,
            args.limit,
        )
    except Exception as error:
        print(f"\nERROR: {error}")
        sys.exit(1)


if __name__ == "__main__":
    main()