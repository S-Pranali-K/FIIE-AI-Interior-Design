from typing import Dict, Any, List


def determine_position(
    x1: float,
    x2: float,
    image_width: float
) -> str:

    center_x = (x1 + x2) / 2

    if center_x < image_width * 0.33:
        return "left"

    if center_x > image_width * 0.66:
        return "right"

    return "center"


def create_spatial_analysis(
    objects: List[Dict[str, Any]],
    image_width: float,
    image_height: float
) -> Dict[str, Any]:

    spatial_objects = []

    for obj in objects:

        box = obj.get("bounding_box", {})

        x1 = box.get("x1", 0)
        x2 = box.get("x2", 0)

        position = determine_position(
            x1,
            x2,
            image_width
        )

        spatial_objects.append(
            {
                "label": obj.get("label"),
                "confidence": obj.get("confidence"),
                "position": position,
                "bounding_box": box,
                "segmented": obj.get("has_mask", False)
            }
        )

    return {
        "image_width": image_width,
        "image_height": image_height,
        "objects": spatial_objects
    }