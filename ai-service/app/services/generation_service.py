from pathlib import Path
from typing import Dict, Any, List

from app.services.controlnet_service import create_structure_control_image


GENERATED_DIR = Path("generated")
GENERATED_DIR.mkdir(exist_ok=True)


def build_generation_prompt(
    requirements: str,
    detected_objects: List[Dict[str, Any]],
    spatial_analysis: Dict[str, Any] | None = None,
) -> str:

    object_names = [
        obj.get("label", "")
        for obj in detected_objects
        if obj.get("label")
    ]

    objects_text = ", ".join(object_names)

    prompt = f"""
Photorealistic interior renovation of the provided room.

USER REQUIREMENT:
{requirements}

EXISTING OBJECTS:
{objects_text}

PRESERVE:
- existing walls
- windows
- doors
- ceiling
- floor structure
- room proportions
- camera viewpoint
- lighting direction

CHANGE ONLY WHAT THE USER REQUESTED.

The redesigned room must look realistic,
functional, professionally designed,
and physically plausible.

Do not change the architecture of the room.
Do not move doors or windows.
Maintain the original camera perspective.
"""

    if spatial_analysis:
        prompt += """

Use the spatial analysis to maintain realistic
positions and relationships between furniture,
walls and other room elements.
"""

    return prompt.strip()


def prepare_generation_request(
    image_path: str,
    requirements: str,
    detected_objects: List[Dict[str, Any]],
    spatial_analysis: Dict[str, Any] | None = None,
) -> Dict[str, Any]:

    control_image = create_structure_control_image(
        image_path
    )

    prompt = build_generation_prompt(
        requirements=requirements,
        detected_objects=detected_objects,
        spatial_analysis=spatial_analysis,
    )

    return {
        "success": True,
        "input_image": image_path,
        "control_image": control_image,
        "prompt": prompt,
        "generation_model": "SDXL + ControlNet",
        "status": "ready_for_generation"
    }