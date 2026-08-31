import os
from pathlib import Path
from typing import Optional

import replicate


OUTPUT_DIR = Path("generated")
OUTPUT_DIR.mkdir(exist_ok=True)


def generate_renovated_image(
    image_path: str,
    prompt: str,
    negative_prompt: Optional[str] = None
) -> str:

    token = os.getenv("REPLICATE_API_TOKEN")

    if not token:
        raise RuntimeError(
            "REPLICATE_API_TOKEN is not configured."
        )

    input_path = Path(image_path)

    if not input_path.exists():
        raise FileNotFoundError(
            f"Input image not found: {image_path}"
        )

    if negative_prompt is None:
        negative_prompt = (
            "blurry, low quality, distorted furniture, "
            "deformed objects, unrealistic architecture, "
            "duplicate furniture, bad perspective"
        )

    with open(input_path, "rb") as image_file:

        output = replicate.run(
           "sdxl-based/realvisxl-v3-multi-controlnet-lora:90a4a3604cd637cb9f1a2bdae1cfa9ed869362ca028814cdce310a78e27daade",
            input={
                "image": image_file,
                "prompt": prompt,
                "negative_prompt": negative_prompt,

                "width": 768,
                "height": 768,

                "num_outputs": 1,
                "num_inference_steps": 30,
                "guidance_scale": 7.5,
                "prompt_strength": 0.8,

                "scheduler": "K_EULER",
                "refine": "no_refiner",

                "controlnet_1": "edge_canny",
                "controlnet_1_image": image_file,
                "controlnet_1_conditioning_scale": 0.8,
                "controlnet_1_start": 0,
                "controlnet_1_end": 1,

                "controlnet_2": "none",
                "controlnet_3": "none",

                "apply_watermark": False,
                "sizing_strategy": "input_image",
            }
        )

    if not output:
        raise RuntimeError(
            "Image generation returned no output."
        )

    output_path = OUTPUT_DIR / "renovated_room.png"

    output_item = output[0]

    with open(output_path, "wb") as output_file:
        output_file.write(output_item.read())

    return str(output_path)