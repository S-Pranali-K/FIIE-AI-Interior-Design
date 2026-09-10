from pathlib import Path

import numpy as np
from PIL import Image
from transformers import pipeline


DEPTH_DIR = Path("depth_maps")
DEPTH_DIR.mkdir(exist_ok=True)


depth_estimator = pipeline(
    "depth-estimation",
    model="Intel/dpt-hybrid-midas"
)


def estimate_depth(image_path: str):
    """
    Estimate depth from a room image and save
    the resulting depth map as a PNG image.
    """

    image = Image.open(image_path).convert("RGB")

    # Run depth estimation
    result = depth_estimator(image)

    depth_image = result["depth"]

    # Convert depth image to grayscale
    depth_array = np.array(depth_image)

    # Normalize depth values to 0-255
    depth_min = depth_array.min()
    depth_max = depth_array.max()

    if depth_max > depth_min:

        normalized_depth = (
            (depth_array - depth_min)
            / (depth_max - depth_min)
            * 255
        )

    else:

        normalized_depth = np.zeros_like(depth_array)

    normalized_depth = normalized_depth.astype(np.uint8)

    # Create PIL image
    depth_map = Image.fromarray(
        normalized_depth,
        mode="L"
    )

    # Save depth map
    output_path = DEPTH_DIR / "room_depth.png"

    depth_map.save(
        output_path,
        format="PNG"
    )

    return {
        "success": True,
        "width": depth_map.width,
        "height": depth_map.height,
        "depth_available": True,
        "depth_map_path": str(output_path)
    }