from typing import List, Dict, Any


OBJECT_CATEGORIES = {
    "furniture": {
        "bed",
        "bench",
        "chair",
        "couch",
        "dining table",
        "potted plant",
    },
    "electronics": {
        "tv",
        "laptop",
        "mouse",
        "remote",
        "keyboard",
        "cell phone",
    },
    "appliances": {
        "microwave",
        "oven",
        "toaster",
        "sink",
        "refrigerator",
    },
    "people": {
        "person",
    },
}


def categorize_object(label: str) -> str:
    """
    Return the environment category for a detected object.
    """
    normalized_label = label.strip().lower()

    for category, labels in OBJECT_CATEGORIES.items():
        if normalized_label in labels:
            return category

    return "other"


def analyze_environment(objects: List[Dict[str, Any]]) -> Dict[str, Any]:
    """
    Analyze YOLO detections and generate environment-level information.
    """

    category_counts = {
        "furniture": 0,
        "electronics": 0,
        "appliances": 0,
        "people": 0,
        "other": 0,
    }

    categorized_objects = []

    for obj in objects:
        label = obj.get("label", "")
        category = categorize_object(label)

        category_counts[category] += 1

        categorized_objects.append(
            {
                "label": label,
                "category": category,
                "confidence": obj.get("confidence"),
                "bounding_box": obj.get("bounding_box"),
            }
        )

    room_type = infer_room_type(category_counts, objects)

    return {
        "room_type": room_type,
        "object_count": len(objects),
        "category_counts": category_counts,
        "objects": categorized_objects,
    }


def infer_room_type(
    category_counts: Dict[str, int],
    objects: List[Dict[str, Any]],
) -> str:
    """
    Infer a basic room/environment type from detected objects.

    This is intentionally rule-based for the first version.
    """

    labels = {
        obj.get("label", "").strip().lower()
        for obj in objects
    }

    if {"bed"} & labels:
        return "bedroom"

    if {"toilet", "sink"} <= labels:
        return "bathroom"

    if {"oven", "microwave", "refrigerator"} & labels:
        return "kitchen"

    if {"couch", "tv"} <= labels:
        return "living_room"

    if category_counts["furniture"] >= 2 and category_counts["electronics"] >= 1:
        return "living_room"

    return "unknown"