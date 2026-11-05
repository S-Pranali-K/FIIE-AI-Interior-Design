# FIIE – Functional Interior Intelligence Engine

An AI-assisted interior design application that helps users explore room redesign concepts using computer vision and AI-powered image generation.

## Project Overview

FIIE is a final-year Computer Science and Engineering project that combines Android development, backend engineering, and AI services to support interior design workflows.

## Screenshot

![FIIE Application Screenshot](screenshots/fiie-dashboard.png)

*Replace this image path with the actual screenshot uploaded to the repository.*

## Key Features

- Room image upload through the Android application.
- Room analysis using computer vision.
- AI-assisted prompt generation.
- Object detection and segmentation.
- AI-based interior redesign workflow.
- Backend integration between the mobile application and AI service.

## Tech Stack

| Layer | Technologies |
|---|---|
| Mobile Application | Android Studio, Java, XML |
| Backend | Java, Spring Boot |
| AI Service | Python, FastAPI |
| Computer Vision | YOLOv8, SAM |
| Image Generation | Stable Diffusion XL, ControlNet |
| Database | PostgreSQL |
| Tools | Git, GitHub, VS Code, Postman |

*Keep only the tools and components actually used in your implementation.*

## System Architecture

```text
Android Application
        |
        v
Spring Boot Backend
        |
        v
Python AI Service
        |
        v
Computer Vision & AI Image Generation
        |
        v
Generated Design Results
        |
        v
Android Application
```

## Setup Instructions

### Prerequisites

- Android Studio
- JDK 17
- Python 3.10 or compatible version
- Maven
- PostgreSQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/S-Pranali-K/FIIE-AI-Interior-Design.git
cd FIIE-AI-Interior-Design
```

### 2. Configure the backend

- Open the backend folder.
- Configure the PostgreSQL database connection in `application.properties`.
- Set the database name, username and password.
- Run the Spring Boot application.

```bash
mvn spring-boot:run
```

### 3. Configure the AI service

- Open the AI service folder.
- Create and activate a Python virtual environment.
- Install the dependencies listed in `requirements.txt`.
- Configure the required model paths and environment variables.
- Start the FastAPI service using the project's entry point.

Example:

```bash
uvicorn main:app --reload
```

*Adjust the command to match the actual Python entry-point filename.*

### 4. Run the Android application

- Open the Android project in Android Studio.
- Configure the backend API URL.
- Build and run the application on an emulator or Android device.

## Project Status

Final-year academic project. Refer to the source code and project documentation for the implemented features and current setup requirements.

## Author

**Pranali Solapure**

[GitHub](https://github.com/S-Pranali-K) · [LinkedIn](https://in.linkedin.com/in/pranali-solapure-aa6380284)
