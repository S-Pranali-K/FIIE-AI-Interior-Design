from pathlib import Path
from typing import Dict, Any, List

import numpy as np
from PIL import Image
from ultralytics import YOLO


SEGMENTATION_DIR = Path("segmentation_masks")
SEGMENTATION_DIR.mkdir(exist_ok=True)


# Lightweight segmentation model.
# It works with common objects such as sofa, chair, table,
# bed, TV, plant, etc.
model = YOLO("yolov8n-seg.pt")


def segment_objects(image_path: str) -> Dict[str, Any]:
    """
    Detect objects and generate pixel-level segmentation masks.

    The function is image-independent:
    it can process different input room/interior images.
    """

    image = Image.open(image_path).convert("RGB")

    results = model(image)

    segmented_objects: List[Dict[str, Any]] = []

    for result in results:

        if result.masks is None or result.boxes is None:
            continue

        names = result.names

        for index, box in enumerate(result.boxes):

            confidence = float(box.conf[0])

            if confidence < 0.40:
                continue

            class_id = int(box.cls[0])
            label = names[class_id]

            x1, y1, x2, y2 = box.xyxy[0].tolist()

            # Get the corresponding segmentation mask.
            mask = result.masks.data[index].cpu().numpy()

            # Convert model mask to binary image.
            mask = (mask > 0.5).astype(np.uint8) * 255

            mask_image = Image.fromarray(mask)

            # Resize mask to original image dimensions.
            mask_image = mask_image.resize(
                image.size,
                Image.Resampling.NEAREST
            )

            mask_filename = (
                f"{label}_{len(segmented_objects)}_mask.png"
            )

            mask_path = SEGMENTATION_DIR / mask_filename

            mask_image.save(mask_path)

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
                    "mask_path": str(mask_path),
                    "has_mask": True,
                }
            )

    return {
        "success": True,
        "input_image": image_path,
        "image_width": image.width,
        "image_height": image.height,
        "object_count": len(segmented_objects),
        "objects": segmented_objects,
    }