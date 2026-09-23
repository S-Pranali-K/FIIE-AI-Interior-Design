import torch
from PIL import Image
from diffusers import StableDiffusionInpaintPipeline


MODEL_PATH = "models/stable-diffusion-inpainting"


def generate_image(
    image_path: str,
    mask_path: str,
    output_path: str,
    prompt: str
):
    print("Loading Stable Diffusion Inpainting model...")

    pipe = StableDiffusionInpaintPipeline.from_pretrained(
        MODEL_PATH,
        torch_dtype=torch.float16,
        safety_checker=None
    )

    # RTX 2050 has only 4 GB VRAM.
    # CPU offload reduces GPU memory usage.
    pipe.enable_model_cpu_offload()

    try:
        pipe.enable_xformers_memory_efficient_attention()
    except Exception:
        print("xformers not available. Continuing without it.")

    image = Image.open(image_path).convert("RGB")
    mask = Image.open(mask_path).convert("L")

    image = image.resize((512, 512))
    mask = mask.resize((512, 512))

    prompt = (
    "Photorealistic interior renovation. "
    + prompt
    + ", realistic room, natural lighting, "
      "professional interior photography, "
      "preserve the existing room structure and proportions."
    )

    negative_prompt = (
        "cartoon, illustration, distorted furniture, "
        "blurry, low quality, unrealistic, deformed"
    )

    print("Generating image...")

    result = pipe(
        prompt=prompt,
        negative_prompt=negative_prompt,
        image=image,
        mask_image=mask,
        num_inference_steps=20,
        guidance_scale=7.5
    )

    result.images[0].save(output_path)

    print("Generation complete.")
    print("Output:", output_path)


if __name__ == "__main__":

    generate_image(
    image_path="input/room.jpg",
    mask_path="input/mask.png",
    output_path="output/generated_room.png",
    prompt="modern grey sofa and elegant furniture"
)