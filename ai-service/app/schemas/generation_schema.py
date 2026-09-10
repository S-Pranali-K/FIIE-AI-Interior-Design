from pydantic import BaseModel
from typing import Optional


class GenerationResponse(BaseModel):
    success: bool
    input_image: str
    control_image: str
    prompt: str
    generation_model: str
    status: str
    generated_image: Optional[str] = None