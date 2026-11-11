
import json
import csv
import argparse


def convert_json_to_csv(input_file, output_file):

    # Load backlog JSON
    with open(input_file, "r", encoding="utf-8") as file:
        backlog = json.load(file)

    epics = backlog.get("epics")

    if not isinstance(epics, list):
        raise ValueError("'epics' must be a list.")

    columns = [
        "Work type",
        "Summary",
        "Description",
        "Work item ID",
        "Parent"
    ]

    rows = []
    next_id = 1

    for epic in epics:

        # Assign a Jira import ID to the epic
        epic_id = next_id
        next_id += 1

        rows.append({
            "Work type": "Epic",
            "Summary": epic["title"],
            "Description": "",
            "Work item ID": epic_id,
            "Parent": ""
        })

        # Add stories belonging to this epic
        for story in epic.get("user_stories", []):

            rows.append({
                "Work type": "Story",
                "Summary": story["title"],
                "Description": story["description"],
                "Work item ID": next_id,
                "Parent": epic_id
            })

            next_id += 1

    # Write CSV
    with open(
        output_file,
        "w",
        newline="",
        encoding="utf-8-sig"
    ) as file:

        writer = csv.DictWriter(
            file,
            fieldnames=columns
        )

        writer.writeheader()
        writer.writerows(rows)

    print("CSV export successful!")
    print(f"Epics: {len(epics)}")
    print(f"Total work items: {len(rows)}")
    print(f"Output: {output_file}")


if __name__ == "__main__":

    parser = argparse.ArgumentParser()

    parser.add_argument("input")
    parser.add_argument(
        "-o",
        "--output",
        default="jira_backlog.csv"
    )

    args = parser.parse_args()

    convert_json_to_csv(args.input, args.output)
