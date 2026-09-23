from ultralytics import YOLO

model = YOLO("yolo11n.pt")

results = model("test_images/room.jpg")

for result in results:
    print("\nDetected objects:")
    print("-" * 40)

    if result.boxes is None or len(result.boxes) == 0:
        print("No objects detected.")
        continue

    for box in result.boxes:
        class_id = int(box.cls[0])
        confidence = float(box.conf[0])
        label = result.names[class_id]

        x1, y1, x2, y2 = box.xyxy[0].tolist()

        print(f"Label       : {label}")
        print(f"Confidence  : {confidence:.2f}")
        print(
            f"Bounding Box: "
            f"{x1:.0f}, {y1:.0f}, "
            f"{x2:.0f}, {y2:.0f}"
        )
        print("-" * 40)