from app.models.yolo_model import model


CONFIDENCE_THRESHOLD = 0.40


def detect_objects(image_path: str):
    results = model(image_path)

    detected_objects = []

    for result in results:
        if result.boxes is None:
            continue

        for box in result.boxes:
            class_id = int(box.cls[0])
            confidence = float(box.conf[0])

            if confidence < CONFIDENCE_THRESHOLD:
                continue

            label = result.names[class_id]

            x1, y1, x2, y2 = box.xyxy[0].tolist()

            detected_objects.append(
                {
                    "label": label,
                    "confidence": round(confidence, 4),
                    "bounding_box": {
                        "x1": round(x1, 2),
                        "y1": round(y1, 2),
                        "x2": round(x2, 2),
                        "y2": round(y2, 2),
                    },
                }
            )

    return detected_objects