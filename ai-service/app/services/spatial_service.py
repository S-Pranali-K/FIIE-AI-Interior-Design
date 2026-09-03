from typing import Dict, Any, List


def determine_horizontal_position(
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


def determine_vertical_position(
    y1: float,
    y2: float,
    image_height: float
) -> str:

    center_y = (y1 + y2) / 2

    if center_y < image_height * 0.33:
        return "top"

    if center_y > image_height * 0.66:
        return "bottom"

    return "middle"


def calculate_object_size(
    x1: float,
    y1: float,
    x2: float,
    y2: float,
    image_width: float,
    image_height: float
) -> Dict[str, Any]:

    object_width = max(0, x2 - x1)
    object_height = max(0, y2 - y1)

    image_area = image_width * image_height
    object_area = object_width * object_height

    area_ratio = 0

    if image_area > 0:
        area_ratio = object_area / image_area

    if area_ratio < 0.05:
        size_category = "small"
    elif area_ratio < 0.20:
        size_category = "medium"
    else:
        size_category = "large"

    return {
        "width": round(object_width, 2),
        "height": round(object_height, 2),
        "area_ratio": round(area_ratio, 4),
        "category": size_category
    }


def normalize_coordinates(
    x1: float,
    y1: float,
    x2: float,
    y2: float,
    image_width: float,
    image_height: float
) -> Dict[str, float]:

    if image_width <= 0 or image_height <= 0:
        return {
            "x1": 0,
            "y1": 0,
            "x2": 0,
            "y2": 0
        }

    return {
        "x1": round(x1 / image_width, 4),
        "y1": round(y1 / image_height, 4),
        "x2": round(x2 / image_width, 4),
        "y2": round(y2 / image_height, 4)
    }


def create_spatial_relationships(
    objects: List[Dict[str, Any]]
) -> List[Dict[str, Any]]:

    relationships = []

    for i in range(len(objects)):

        object_a = objects[i]

        for j in range(i + 1, len(objects)):

            object_b = objects[j]

            box_a = object_a["bounding_box"]
            box_b = object_b["bounding_box"]

            center_ax = (box_a["x1"] + box_a["x2"]) / 2
            center_ay = (box_a["y1"] + box_a["y2"]) / 2

            center_bx = (box_b["x1"] + box_b["x2"]) / 2
            center_by = (box_b["y1"] + box_b["y2"]) / 2

            horizontal_relation = "same horizontal area"
            vertical_relation = "same vertical area"

            if center_ax < center_bx:
                horizontal_relation = "left of"
            elif center_ax > center_bx:
                horizontal_relation = "right of"

            if center_ay < center_by:
                vertical_relation = "above"
            elif center_ay > center_by:
                vertical_relation = "below"

            relationships.append(
                {
                    "object_a": object_a["label"],
                    "object_b": object_b["label"],
                    "horizontal_relation": horizontal_relation,
                    "vertical_relation": vertical_relation
                }
            )

    return relationships


def create_spatial_analysis(
    objects: List[Dict[str, Any]],
    image_width: float,
    image_height: float
) -> Dict[str, Any]:

    spatial_objects = []

    for obj in objects:

        box = obj.get("bounding_box", {})

        x1 = float(box.get("x1", 0))
        y1 = float(box.get("y1", 0))
        x2 = float(box.get("x2", 0))
        y2 = float(box.get("y2", 0))

        horizontal_position = determine_horizontal_position(
            x1,
            x2,
            image_width
        )

        vertical_position = determine_vertical_position(
            y1,
            y2,
            image_height
        )

        object_size = calculate_object_size(
            x1,
            y1,
            x2,
            y2,
            image_width,
            image_height
        )

        normalized_box = normalize_coordinates(
            x1,
            y1,
            x2,
            y2,
            image_width,
            image_height
        )

        spatial_objects.append(
            {
                "label": obj.get("label"),
                "confidence": obj.get("confidence"),
                "bounding_box": box,
                "normalized_coordinates": normalized_box,
                "horizontal_position": horizontal_position,
                "vertical_position": vertical_position,
                "size": object_size,
                "segmented": obj.get("has_mask", False)
            }
        )

    relationships = create_spatial_relationships(
        spatial_objects
    )

    return {
        "success": True,
        "image_width": image_width,
        "image_height": image_height,
        "object_count": len(spatial_objects),
        "objects": spatial_objects,
        "relationships": relationships
    }