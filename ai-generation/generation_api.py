from pathlib import Path
from uuid import uuid4

from fastapi import FastAPI, File, Form, UploadFile, HTTPException
from fastapi.responses import FileResponse

from generate import generate_image


app = FastAPI(
    title="FIIE AI Generation Service",
    version="1.0.0"
)

BASE_DIR = Path(__file__).resolve().parent
UPLOAD_DIR = BASE_DIR / "input"
OUTPUT_DIR = BASE_DIR / "output"

UPLOAD_DIR.mkdir(exist_ok=True)
OUTPUT_DIR.mkdir(exist_ok=True)


@app.get("/")
def root():
    return {
        "success": True,
        "service": "FIIE AI Generation Service",
        "status": "running"
    }


@app.get("/health")
def health():
    return {
        "success": True,
        "status": "healthy"
    }


@app.post("/api/v1/generate")
async def generate(
    image: UploadFile = File(...),
    mask: UploadFile = File(...),
    requirement: str = Form(...)
):
    request_id = str(uuid4())

    image_path = UPLOAD_DIR / f"{request_id}_room.jpg"
    mask_path = UPLOAD_DIR / f"{request_id}_mask.png"
    output_path = OUTPUT_DIR / f"{request_id}_generated.png"

    try:
        with open(image_path, "wb") as buffer:
            buffer.write(await image.read())

        with open(mask_path, "wb") as buffer:
            buffer.write(await mask.read())

        generate_image(
            image_path=str(image_path),
            mask_path=str(mask_path),
            output_path=str(output_path),
            prompt=requirement
        )

        if not output_path.exists():
            raise HTTPException(
                status_code=500,
                detail="Image generation failed."
            )

        return FileResponse(
            path=str(output_path),
            media_type="image/png",
            filename="generated_room.png"
        )

    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail=str(e)
        )