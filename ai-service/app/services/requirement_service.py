import re
from typing import Dict, Any


def understand_requirements(requirements: str) -> Dict[str, Any]:
    """
    Convert the user's renovation request into a basic structured plan.
    """

    text = requirements.strip()
    lower_text = text.lower()

    action = "modify"
    target = None
    new_object = None
    style = None
    location = None

    # Detect action
    if any(word in lower_text for word in ["replace", "change", "swap"]):
        action = "replace"
    elif any(word in lower_text for word in ["remove", "delete"]):
        action = "remove"
    elif any(word in lower_text for word in ["add", "insert", "place"]):
        action = "add"

    # Detect common target objects
    known_objects = [
        "table",
        "chair",
        "sofa",
        "couch",
        "bed",
        "tv",
        "cabinet",
        "wardrobe",
        "desk",
        "plant",
        "lamp",
    ]

    for obj in known_objects:
        if obj in lower_text:
            target = obj
            break

    # Detect common new furniture/object terms
    new_objects = [
        "study table",
        "dining table",
        "coffee table",
        "side table",
        "office chair",
        "study chair",
        "sofa",
        "couch",
        "bed",
        "wardrobe",
        "cabinet",
        "bookshelf",
        "plant",
        "lamp",
    ]

    for obj in new_objects:
        if obj in lower_text:
            new_object = obj
            break

    # Detect styles
    styles = [
        "modern",
        "minimalist",
        "traditional",
        "contemporary",
        "luxury",
        "classic",
        "industrial",
        "wooden",
    ]

    for item in styles:
        if item in lower_text:
            style = item
            break

    # Detect common locations
    locations = [
        "near the window",
        "near window",
        "next to the window",
        "next to window",
        "near the door",
        "near door",
        "against the wall",
        "in the corner",
        "left side",
        "right side",
        "center",
        "centre",
    ]

    for item in locations:
        if item in lower_text:
            location = item
            break

    return {
        "original_request": text,
        "action": action,
        "target": target,
        "new_object": new_object,
        "style": style,
        "location": location,
    }