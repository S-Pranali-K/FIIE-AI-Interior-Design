from typing import Dict, Any


DEFAULT_PRICES = {
    "study table": 8000,
    "dining table": 12000,
    "coffee table": 6000,
    "side table": 3500,
    "sofa": 18000,
    "couch": 18000,
    "chair": 4000,
    "office chair": 6000,
    "study chair": 5000,
    "bed": 20000,
    "wardrobe": 25000,
    "cabinet": 10000,
    "bookshelf": 7000,
    "lamp": 2500,
    "plant": 1500,
}


def estimate_budget(
    new_object: str | None,
    budget: float | None,
    quantity: int = 1,
) -> Dict[str, Any]:

    if not new_object:
        return {
            "currency": "INR",
            "estimated_cost": 0,
            "budget": budget,
            "within_budget": None,
            "item": None,
            "quantity": quantity,
        }

    normalized = new_object.lower().strip()

    estimated_unit_price = DEFAULT_PRICES.get(
        normalized,
        5000
    )

    estimated_cost = estimated_unit_price * quantity

    within_budget = None

    if budget is not None:
        within_budget = estimated_cost <= budget

    return {
        "currency": "INR",
        "item": new_object,
        "quantity": quantity,
        "estimated_unit_price": estimated_unit_price,
        "estimated_cost": estimated_cost,
        "budget": budget,
        "within_budget": within_budget,
    }