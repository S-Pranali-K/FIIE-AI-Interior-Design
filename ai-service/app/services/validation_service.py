from typing import Dict, Any


def validate_request(
    requirements: Dict[str, Any],
    matching_result: Dict[str, Any]
) -> Dict[str, Any]:
    """
    Validate whether the requested modification can be
    safely continued based on detected image objects.
    """

    action = requirements.get("action")
    target = requirements.get("target")

    target_found = matching_result.get("target_found", False)

    # Requests that do not require an existing object
    if action == "add":
        return {
            "valid": True,
            "status": "ready",
            "message": "Request is valid for adding a new object."
        }

    # Global style modifications do not require a target object
    if action == "modify" and not target:
        return {
            "valid": True,
            "status": "ready",
            "message": "General room modification can continue."
        }

    # Replace/remove requires the existing object
    if action in ["replace", "remove"]:

        if target_found:
            return {
                "valid": True,
                "status": "ready",
                "message": f"Requested target '{target}' was detected."
            }

        return {
            "valid": False,
            "status": "target_not_found",
            "message": (
                f"The requested target '{target}' "
                "was not detected in the image."
            )
        }

    return {
        "valid": True,
        "status": "ready",
        "message": "Request can continue."
    }