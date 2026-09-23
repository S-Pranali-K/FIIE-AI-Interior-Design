from typing import List, Dict, Any

from app.services.requirement_service import understand_requirements


def create_design_plan(
    objects: List[Dict[str, Any]],
    requirements: str,
    budget: float | None = None,
    vastu_enabled: bool = False,
) -> Dict[str, Any]:

    requirement_plan = understand_requirements(requirements)

    detected_labels = [
        obj.get("label", "").lower()
        for obj in objects
    ]

    target = requirement_plan.get("target")

    target_detected = False

    if target:
        target_detected = any(
            target in label or label in target
            for label in detected_labels
        )

    return {
        "requirements": requirement_plan,
        "detected_objects": objects,
        "target_detected": target_detected,
        "budget": budget,
        "vastu_enabled": vastu_enabled,
    }