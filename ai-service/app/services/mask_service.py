from pathlib import Path
from typing import Dict, Any

from PIL import Image, ImageDraw


MASK_DIR = Path("masks")
MASK_DIR.mkdir(exist_ok=True)


def create_object_mask(
    image_path: str,
    bounding_box: Dict[str, float],
    output_name: str = "object_mask.png"
) -> str:
    """
    Create a binary mask for the requested object.

    White = area to edit
    Black = area to preserve
    """

    image = Image.open(image_path).convert("RGB")

    width, height = image.size

    x1 = int(max(0, bounding_box.get("x1", 0)))
    y1 = int(max(0, bounding_box.get("y1", 0)))
    x2 = int(min(width, bounding_box.get("x2", width)))
    y2 = int(min(height, bounding_box.get("y2", height)))

    mask = Image.new(
        "L",
        (width, height),
        0
    )

    draw = ImageDraw.Draw(mask)

    draw.rectangle(
        [x1, y1, x2, y2],
        fill=255
    )

    output_path = MASK_DIR / output_name

    mask.save(
        output_path,
        format="PNG"
    )

    return str(output_path)