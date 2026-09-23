import cv2
from pathlib import Path


ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"}


def validate_image(image_path: str):
    path = Path(image_path)

    if not path.exists():
        raise ValueError("Image file does not exist.")

    if path.suffix.lower() not in ALLOWED_EXTENSIONS:
        raise ValueError(
            "Unsupported image format. "
            "Only JPG, JPEG, and PNG are allowed."
        )

    image = cv2.imread(str(path))

    if image is None:
        raise ValueError("Unable to read image.")

    height, width = image.shape[:2]

    if width <= 0 or height <= 0:
        raise ValueError("Invalid image dimensions.")

    return {
        "valid": True,
        "width": width,
        "height": height,
        "format": path.suffix.lower(),
    }