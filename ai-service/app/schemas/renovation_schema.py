from typing import Optional, List, Dict, Any
from pydantic import BaseModel, Field


class RenovationRequest(BaseModel):
    requirements: str = Field(
        ...,
        min_length=3,
        description="User's interior renovation requirements"
    )

    budget: Optional[float] = Field(
        default=None,
        ge=0,
        description="Maximum renovation budget in INR"
    )

    vastu_enabled: bool = Field(
        default=False,
        description="Whether Vastu recommendations are required"
    )


class RenovationResponse(BaseModel):
    success: bool
    requirements: str
    budget: Optional[float]
    vastu_enabled: bool
    objects: List[Dict[str, Any]]