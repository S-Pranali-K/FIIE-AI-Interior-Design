from pathlib import Path
from uuid import uuid4

from fastapi import APIRouter, File, UploadFile, HTTPException

from app.services.image_service import validate_image
from app.services.detection_service import detect_objects
from app.schemas.detection_schema import DetectionResponse


router = APIRouter(
    prefix="/api/v1",
    tags=["AI Analysis"]
)


UPLOAD_DIR = Path("uploads")
UPLOAD_DIR.mkdir(exist_ok=True)

MAX_FILE_SIZE = 10 * 1024 * 1024  # 10 MB

ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"}


@router.post("/analyze", response_model=DetectionResponse)
async def analyze_image(file: UploadFile = File(...)):

    if not file.filename:
        raise HTTPException(
            status_code=400,
            detail="No file provided."
        )

    extension = Path(file.filename).suffix.lower()

    if extension not in ALLOWED_EXTENSIONS:
        raise HTTPException(
            status_code=400,
            detail="Only JPG, JPEG, and PNG images are supported."
        )

    contents = await file.read()

    if not contents:
        raise HTTPException(
            status_code=400,
            detail="Uploaded file is empty."
        )

    if len(contents) > MAX_FILE_SIZE:
        raise HTTPException(
            status_code=413,
            detail="Image file is too large. Maximum allowed size is 10 MB."
        )

    unique_filename = f"{uuid4()}{extension}"
    file_path = UPLOAD_DIR / unique_filename

    try:
        file_path.write_bytes(contents)

        image_info = validate_image(str(file_path))

        objects = detect_objects(str(file_path))

        return {
            "success": True,
            "image": image_info,
            "objects": objects
        }

    except HTTPException:
        raise

    except ValueError as e:
        raise HTTPException(
            status_code=400,
            detail=str(e)
        )

    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail=f"AI processing failed: {str(e)}"
        )

    finally:
        if file_path.exists():
            try:
                file_path.unlink()
            except OSError:
                pass