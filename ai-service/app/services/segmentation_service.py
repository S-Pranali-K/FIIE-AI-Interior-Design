from ultralytics import YOLO


SEGMENTATION_MODEL = "yolo11n-seg.pt"

segmentation_model = YOLO(SEGMENTATION_MODEL)


def segment_objects(image_path: str):
    results = segmentation_model(image_path)

    segmented_objects = []

    for result in results:
        if result.masks is None or result.boxes is None:
            continue

        for i, box in enumerate(result.boxes):
            class_id = int(box.cls[0])
            confidence = float(box.conf[0])
            label = result.names[class_id]

            x1, y1, x2, y2 = box.xyxy[0].tolist()

            segmented_objects.append(
                {
                    "label": label,
                    "confidence": round(confidence, 4),
                    "bounding_box": {
                        "x1": round(x1, 2),
                        "y1": round(y1, 2),
                        "x2": round(x2, 2),
                        "y2": round(y2, 2),
                    },
                    "has_mask": True,
                }
            )

    return segmented_objects