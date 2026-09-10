from PIL import Image

from app.services.mask_service import create_object_mask
from app.services.segmentation_service import segment_objects

from app.services.requirement_service import understand_requirements
from app.services.detection_service import detect_objects
from app.services.spatial_service import create_spatial_analysis
from app.services.depth_service import estimate_depth
from app.services.generation_service import prepare_generation_request
from app.services.matching_service import match_requirements_to_objects


def process_room_request(
    image_path: str,
    user_requirement: str
):
    """
    Main AI processing pipeline.

    Flow:
    Image
        ↓
    Requirement understanding
        ↓
    Object detection
        ↓
    Requirement/Object matching
        ↓
    Object segmentation
        ↓
    Spatial analysis
        ↓
    Depth estimation
        ↓
    Generation request preparation
    """

    # Load input image
    image = Image.open(image_path)

    image_width, image_height = image.size

    # 1. Understand user's natural-language requirement
    requirements = understand_requirements(
        user_requirement
    )

    # 2. Detect objects actually present in image
    detected_objects = detect_objects(
        image_path
    )

    # 3. Match user's requested object with
    #    objects actually detected by YOLO
    matching = match_requirements_to_objects(
        requirements,
        detected_objects
    )

    # 4. Create precise segmentation mask
    #    for the matched target object.
    #
    #    Bounding-box mask is used as fallback
    #    if segmentation is unavailable.

    object_mask = None
    segmentation_result = None

    if matching.get("target_found") and matching.get("matches"):

        matched_object = matching["matches"][0]

        try:
            # Run segmentation
            segmentation_result = segment_objects(
                image_path
            )

            matched_label = (
                matched_object.get("label", "")
                .lower()
                .strip()
            )

            # Find segmentation mask corresponding
            # to the matched object.
            for segmented_object in segmentation_result.get(
                "objects", []
            ):

                segmented_label = (
                    segmented_object.get("label", "")
                    .lower()
                    .strip()
                )

                if segmented_label == matched_label:

                    object_mask = segmented_object.get(
                        "mask_path"
                    )

                    break

        except Exception as e:

            segmentation_result = {
                "success": False,
                "error": str(e)
            }

        # Fallback to bounding-box mask
        if object_mask is None:

            object_mask = create_object_mask(
                image_path=image_path,
                bounding_box=matched_object["bounding_box"]
            )

    # Stop safely if the requested target object
    # was not detected in the image.
    if (
        requirements.get("target")
        and not matching.get("target_found")
    ):

        return {
            "success": False,
            "input_image": image_path,
            "error": "Requested object was not detected in the image.",
            "requirements": requirements,
            "detected_objects": detected_objects,
            "matching": matching,
            "object_mask": None,
            "segmentation": None
        }

    # 5. Spatial analysis
    spatial_analysis = create_spatial_analysis(
        detected_objects,
        image_width,
        image_height
    )

    # 6. Depth estimation
    depth_analysis = estimate_depth(
        image_path
    )

    # 7. Prepare generation request
    generation_request = prepare_generation_request(
        image_path=image_path,
        requirements=user_requirement,
        detected_objects=detected_objects,
        spatial_analysis=spatial_analysis
    )

    # Final pipeline result
    return {
        "success": True,
        "input_image": image_path,

        "image_dimensions": {
            "width": image_width,
            "height": image_height
        },

        "requirements": requirements,

        "detected_objects": detected_objects,

        "matching": matching,

        "object_mask": object_mask,

        "segmentation": segmentation_result,

        "spatial_analysis": spatial_analysis,

        "depth_analysis": depth_analysis,

        "generation_request": generation_request
    }