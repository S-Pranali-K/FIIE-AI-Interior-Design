from pydantic import BaseModel
from typing import List


class BoundingBox(BaseModel):
    x1: float
    y1: float
    x2: float
    y2: float


class DetectedObject(BaseModel):
    label: str
    confidence: float
    bounding_box: BoundingBox


class ImageInfo(BaseModel):
    valid: bool
    width: int
    height: int
    format: str


class DetectionResponse(BaseModel):
    success: bool
    image: ImageInfo
    objects: List[DetectedObject]