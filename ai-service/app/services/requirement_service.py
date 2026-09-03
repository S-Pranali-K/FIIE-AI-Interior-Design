from typing import Dict, Any


def parse_user_requirement(requirements: str) -> Dict[str, Any]:
    """
    Convert a natural-language renovation request into
    a structured representation.

    This parser is intentionally simple and deterministic.
    It does not generate images.
    """

    if not requirements or not requirements.strip():
        raise ValueError("Requirement cannot be empty.")

    text = requirements.strip()
    lower_text = text.lower()

    # ---------------------------------------------------------
    # ACTION
    # ---------------------------------------------------------

    action = "modify"

    if any(word in lower_text for word in [
        "replace",
        "swap",
        "change",
    ]):
        action = "replace"

    elif any(word in lower_text for word in [
        "remove",
        "delete",
        "take away",
    ]):
        action = "remove"

    elif any(word in lower_text for word in [
        "add",
        "insert",
        "place",
        "put",
    ]):
        action = "add"

    # ---------------------------------------------------------
    # OBJECTS
    # ---------------------------------------------------------

    object_names = [
        "study table",
        "dining table",
        "coffee table",
        "side table",
        "office chair",
        "study chair",
        "bookshelf",
        "wardrobe",
        "cabinet",
        "sofa",
        "couch",
        "chair",
        "table",
        "bed",
        "desk",
        "tv",
        "plant",
        "lamp",
    ]

    # Longer phrases must be checked first.
    detected_objects = []

    for obj in object_names:
        if obj in lower_text:
            detected_objects.append(obj)

    # Remove duplicates while preserving order.
    detected_objects = list(dict.fromkeys(detected_objects))

    # ---------------------------------------------------------
    # TARGET / NEW OBJECT
    # ---------------------------------------------------------

    target = None
    new_object = None

    if action == "replace":
        # Example:
        # "Replace the sofa with a modern sofa"
        #
        # Try to identify the object being replaced.
        for obj in object_names:
            if f"replace the {obj}" in lower_text:
                target = obj
                break

            if f"replace {obj}" in lower_text:
                target = obj
                break

        # If no explicit pattern matched, use first detected object.
        if target is None and detected_objects:
            target = detected_objects[0]

        # Look for object after "with".
        if " with " in lower_text:
            after_with = lower_text.split(" with ", 1)[1]

            for obj in object_names:
                if obj in after_with:
                    new_object = obj
                    break

    elif action == "add":
        # For "Add a study table", the new object is
        # the detected furniture item.
        if detected_objects:
            new_object = detected_objects[0]

    elif action == "remove":
        if detected_objects:
            target = detected_objects[0]

    # ---------------------------------------------------------
    # STYLE
    # ---------------------------------------------------------

    style = None

    style_aliases = {
        "modern": "modern",
        "minimalist": "minimalist",
        "minimal": "minimalist",
        "traditional": "traditional",
        "contemporary": "contemporary",
        "luxury": "luxury",
        "luxurious": "luxury",
        "classic": "classic",
        "industrial": "industrial",
        "wooden": "wooden",
    }

    for word, normalized_style in style_aliases.items():
        if word in lower_text:
            style = normalized_style
            break

    # ---------------------------------------------------------
    # LOCATION
    # ---------------------------------------------------------

    location = None

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

    # ---------------------------------------------------------
    # STRUCTURED REQUIREMENTS
    # ---------------------------------------------------------

    structured_requirements = [action]

    if target:
        structured_requirements.append(f"target:{target}")

    if new_object:
        structured_requirements.append(f"new_object:{new_object}")

    if style:
        structured_requirements.append(f"style:{style}")

    if location:
        structured_requirements.append(f"location:{location}")

    return {
        "original_request": text,
        "action": action,
        "target": target,
        "new_object": new_object,
        "style": style,
        "location": location,
        "detected_objects": detected_objects,
        "requirements": structured_requirements,
    }


# Backward-compatible function name.
# Existing code that imports understand_requirements
# will continue to work.
def understand_requirements(requirements: str) -> Dict[str, Any]:
    return parse_user_requirement(requirements)