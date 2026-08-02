from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

app = FastAPI()

# 1. Java වල DTO එකකට සමානයි (Request structure එක validate කරන්න)
class ItemDTO(BaseModel):
    name: str
    price: float
    description: str | None = None

# 2. GET Request එකක් Handle කරන හැටි (Java වල @GetMapping වගේ)
@app.get("/api/v1/item/{item_id}")
def get_item(item_id: int):
    # Python 3.10+ වල තියෙන match-case පාවිච්චි කරලා කොන්දේසි බලමු
    match item_id:
        case 1:
            return {"item_id": 1, "name": "Dental Kit", "price": 1500.0}
        case 2:
            return {"item_id": 2, "name": "Mouthwash", "price": 850.0}
        case _:
            raise HTTPException(status_code=404, detail="Item not found")

# 3. POST Request එකක් Handle කරන හැටි (Java වල @PostMapping වගේ)
@app.post("/api/v1/item")
def create_item(item: ItemDTO):
    # එන Request JSON data ටික extract කරගෙන මෙහෙම response එකක් දෙනවා
    return {
        "status": "Item created successfully!",
        "item_name": item.name,
        "item_price": item.price
    }