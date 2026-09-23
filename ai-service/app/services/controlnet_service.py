from pathlib import Path
import numpy as np
from PIL import Image, ImageFilter


CONTROL_DIR = Path("control_inputs")
CONTROL_DIR.mkdir(exist_ok=True)


def create_structure_control_image(
    image_path: str,
    output_name: str = "room_structure.png"
) -> str:
    """
    Prepare a structural control image for the future
    SDXL + ControlNet generation pipeline.

    For the first version we use edge information.
    Later we will combine this with the depth map.
    """

    image = Image.open(image_path).convert("RGB")

    # Resize to a manageable working resolution
    image.thumbnail((1024, 1024))

    # Convert to grayscale
    gray = image.convert("L")

    # Detect edges using PIL
    edges = gray.filter(ImageFilter.FIND_EDGES)

    # Increase contrast
    edges_array = np.array(edges)

    threshold = 40

    edges_array = np.where(
        edges_array > threshold,
        255,
        0
    ).astype(np.uint8)

    control_image = Image.fromarray(
        edges_array,
        mode="L"
    ).convert("RGB")

    output_path = CONTROL_DIR / output_name

    control_image.save(
        output_path,
        format="PNG"
    )

    return str(output_path)