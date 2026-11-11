
import re
import json
import argparse
from pathlib import Path


def extract_fields(text, required_fields):
    """Extract fields from an epic or user story block."""

    pattern = (
        r'\b(ID|TITLE|DESCRIPTION)\s*:\s*'
        r'(.*?)'
        r'(?=\s+\b(?:ID|TITLE|DESCRIPTION)\s*:|$)'
    )

    fields = {}

    for match in re.finditer(pattern, text.strip(), re.DOTALL):
        key = match.group(1).lower()
        value = match.group(2).strip()

        if key in fields:
            raise ValueError(f"Duplicate field: {key}")

        fields[key] = value

    for field in required_fields:
        if not fields.get(field):
            raise ValueError(f"Missing required field: {field}")

    return fields


def parse_story(story_text):
    fields = extract_fields(
        story_text,
        ["id", "title", "description"]
    )

    return {
        "id": fields["id"],
        "title": fields["title"],
        "description": fields["description"]
    }


def parse_epic(epic_text):
    # Extract all user stories belonging to this epic.
    story_pattern = (
        r'\[USER_STORY_START\](.*?)\[USER_STORY_END\]'
    )

    story_matches = list(
        re.finditer(story_pattern, epic_text, re.DOTALL)
    )

    # Remove stories to isolate the epic's own fields.
    epic_header = re.sub(
        story_pattern,
        "",
        epic_text,
        flags=re.DOTALL
    )

    epic_fields = extract_fields(
        epic_header,
        ["id", "title"]
    )

    stories = [
        parse_story(match.group(1))
        for match in story_matches
    ]

    return {
        "id": epic_fields["id"],
        "title": epic_fields["title"],
        "user_stories": stories
    }


def convert_to_json(text):
    epic_pattern = r'\[EPIC_START\](.*?)\[EPIC_END\]'

    epic_matches = list(
        re.finditer(epic_pattern, text, re.DOTALL)
    )

    if not epic_matches:
        raise ValueError("No valid epics found.")

    # Ensure there is no unrecognized content outside epic blocks.
    remaining_text = re.sub(
        epic_pattern,
        "",
        text,
        flags=re.DOTALL
    ).strip()

    if remaining_text:
        raise ValueError("Unexpected content outside epic blocks.")

    epics = [
        parse_epic(match.group(1))
        for match in epic_matches
    ]

    # Validate unique IDs across all epics and stories.
    all_ids = []

    for epic in epics:
        all_ids.append(epic["id"])

        for story in epic["user_stories"]:
            all_ids.append(story["id"])

    if len(all_ids) != len(set(all_ids)):
        raise ValueError("Duplicate epic or user story IDs found.")

    return {"epics": epics}


def main():
    parser = argparse.ArgumentParser(
        description="Convert Qwen backlog output into JSON."
    )

    parser.add_argument("input", help="Input text file")
    parser.add_argument(
        "-o",
        "--output",
        default="backlog.json",
        help="Output JSON file"
    )

    args = parser.parse_args()

    input_path = Path(args.input)
    output_path = Path(args.output)

    try:
        text = input_path.read_text(encoding="utf-8")

        result = convert_to_json(text)

        output_path.write_text(
            json.dumps(result, indent=4, ensure_ascii=False),
            encoding="utf-8"
        )

        print(f"Successfully converted {len(result['epics'])} epics.")

        story_count = sum(
            len(epic["user_stories"])
            for epic in result["epics"]
        )

        print(f"Total user stories: {story_count}")
        print(f"JSON saved to: {output_path}")

    except (OSError, ValueError) as error:
        print(f"Conversion failed: {error}")


if __name__ == "__main__":
    main()
