from typing import Dict, Any, List


def analyze_vastu(
    requirements: str,
    vastu_enabled: bool,
) -> Dict[str, Any]:

    if not vastu_enabled:
        return {
            "enabled": False,
            "recommendations": []
        }

    text = requirements.lower()

    recommendations: List[str] = []

    if "study table" in text or "study desk" in text:
        recommendations.append(
            "Consider a study-table placement that supports a suitable study orientation."
        )

    if "bed" in text or "bedroom" in text:
        recommendations.append(
            "Consider maintaining clear movement space around the bed."
        )

    if "sofa" in text or "couch" in text:
        recommendations.append(
            "Consider placing seating without obstructing the main room circulation."
        )

    if not recommendations:
        recommendations.append(
            "Review the proposed furniture placement according to the selected Vastu requirements."
        )

    return {
        "enabled": True,
        "recommendations": recommendations
    }