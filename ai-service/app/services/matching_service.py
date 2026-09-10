from typing import Dict, Any, List


def normalize_label(label: str) -> str:
    """
    Normalize object names so user language
    and YOLO labels can be compared.
    """
    if not label:
        return ""

    label = label.lower().strip()

    aliases = {
        # Furniture
        "couch": "sofa",
        "settee": "sofa",
        "loveseat": "sofa",
        "desk": "table",
        "dining table": "table",
        "dining-table": "table",
        "seat": "chair",

        # Electronics
        "television": "tv",
        "monitor": "tv",
        "screen": "tv",

        # Plants
        "potted plant": "plant",
        "indoor plant": "plant",
        "house plant": "plant",

        # Storage
        "cabinet": "storage",
        "cupboard": "storage",
    }

    return aliases.get(label, label)


def match_requirements_to_objects(
    requirements: Dict[str, Any],
    detected_objects: List[Dict[str, Any]]
) -> Dict[str, Any]:
    """
    Match the object requested by the user with objects
    actually detected in the image.
    """

    requested_target = requirements.get("target")
    requested_new_object = requirements.get("new_object")
    action = requirements.get("action")

    requested_target_normalized = (
        normalize_label(requested_target)
        if requested_target
        else None
    )

    matches = []

    for obj in detected_objects:
        detected_label = obj.get("label")

        if not detected_label:
            continue

        detected_normalized = normalize_label(detected_label)

        if (
            requested_target_normalized
            and detected_normalized == requested_target_normalized
        ):
            matches.append({
                "label": detected_label,
                "normalized_label": detected_normalized,
                "confidence": obj.get("confidence"),
                "bounding_box": obj.get("bounding_box"),
                "match": True
            })

    target_found = len(matches) > 0

    return {
        "success": True,
        "action": action,
        "requested_target": requested_target,
        "requested_target_normalized": requested_target_normalized,
        "requested_new_object": requested_new_object,
        "target_found": target_found,
        "matches": matches,
        "detected_object_count": len(detected_objects)
    }