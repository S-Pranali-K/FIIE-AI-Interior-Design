from PIL import Image
import torch
from transformers import pipeline


depth_estimator = pipeline(
    "depth-estimation",
    model="Intel/dpt-hybrid-midas"
)


def estimate_depth(image_path: str):

    image = Image.open(image_path).convert("RGB")

    result = depth_estimator(image)

    depth_image = result["depth"]

    return {
        "success": True,
        "width": depth_image.width,
        "height": depth_image.height,
        "depth_available": True
    }