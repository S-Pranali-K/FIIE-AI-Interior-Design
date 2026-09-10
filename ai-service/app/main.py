from fastapi import FastAPI

from app.api.routes import router


app = FastAPI(
    title="FIIE AI Service",
    description="AI service for interior image analysis and furniture detection",
    version="1.0.0"
)


@app.get("/health")
def health_check():
    return {
        "status": "ok",
        "service": "ai-service"
    }


app.include_router(router)