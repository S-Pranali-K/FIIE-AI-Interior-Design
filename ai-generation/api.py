from fastapi import FastAPI

app = FastAPI(
    title="FIIE AI Image Generation Service",
    version="1.0.0"
)


@app.get("/")
def root():
    return {
        "success": True,
        "service": "FIIE AI Image Generation Service",
        "status": "running"
    }


@app.get("/health")
def health():
    return {
        "success": True,
        "status": "healthy"
    }