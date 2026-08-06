Markdown
# 🅿️ Polyglot Microservices Parking & Booking Management System

A distributed, polyglot microservices-based parking spot management and booking system built using **Java (Spring Boot)** and **Python (Flask)**. This project demonstrates service discovery, declarative inter-service communication, real-time booking operations, and owner analytics.

---

## 📐 System Architecture Overview

graph TD
    UI[Client / Postman] -->|HTTP Requests| Eureka[Eureka Service Discovery - Port: 8761]
    
    subgraph Spring Cloud Ecosystem
        Eureka <-->|Register & Discover| ParkingSpaceService[Parking Space Service - Spring Boot :8090]
        Eureka <-->|Register & Discover| ParkingSpotService[Parking Spot Service - Python Flask :8087]
    end

    ParkingSpaceService -->|OpenFeign Call| ParkingSpotService
    ParkingSpaceService --> DB1[(MySQL Database)]
    ParkingSpotService --> DB2[(SQLite / MySQL Database)]
🛠️ Tech Stack
Domain	Technology / Framework
Primary Backend Service	Java 17, Spring Boot 3.x, Spring Data JPA, Lombok, ModelMapper
Secondary Backend Service	Python 3.x, Flask, SQLAlchemy
Service Discovery	Spring Cloud Netflix Eureka, py-eureka-client
Inter-Service Communication	Spring Cloud OpenFeign
Databases	MySQL, SQLite
Build Tools & APIs	Maven, Pip, RESTful APIs
📁 Repository & Project Structure
1. Spring Boot Service (parking-space-sevice)
Plaintext
parking-space-sevice/
├── src/main/java/lk/ijse/parkingspacesevice/
│   ├── api/                     # REST Controllers
│   │   └── ParkingSpotController.java
│   ├── config/                  # App Configurations
│   │   └── AppConfig.java
│   ├── dto/                     # Data Transfer Objects
│   │   ├── BookingDTO.java
│   │   ├── OwnerDashboardDTO.java
│   │   └── OwnerRevenueReportDTO.java
│   ├── entity/                  # JPA Entities
│   │   └── ParkingSpot.java
│   ├── feign/                   # OpenFeign Clients
│   │   └── BookingServiceClient.java
│   ├── repo/                    # JPA Repositories
│   │   └── ParkingSpotRepository.java
│   └── service/                 # Business Logic Interfaces & Impl
│       ├── impl/
│       │   └── ParkingSpotServiceImpl.java
│       └── ParkingSpotService.java
└── pom.xml
2. Python Flask Service (parking-spot-service)
Plaintext
parking-spot-service/
├── app/
│   ├── controllers/             # Flask Blueprints & Routes
│   │   └── parking_spot_controller.py
│   ├── models/                  # SQLAlchemy Database Models
│   │   └── parking_spot.py
│   ├── config.py                # Database & Eureka Configuration
│   └── extensions.py            # SQLAlchemy Instance
├── app.py                       # Application Entrypoint & Eureka Init
└── requirements.txt
🔄 End-to-End Implementation Process
Code snippet
sequenceDiagram
    autonumber
    actor Client
    participant Spring as Parking Space Service (Java)
    participant Feign as OpenFeign Client
    participant Eureka as Eureka Server
    participant Flask as Parking Spot Service (Python)

    Note over Spring, Flask: Service Registration Phase
    Spring->>Eureka: Register as 'PARKING-SPACE-SERVICE'
    Flask->>Eureka: Register as 'PARKING-SPOT-SERVICE' via py-eureka-client

    Note over Client, Flask: Owner Revenue Report Workflow
    Client->>Spring: GET /api/v1/parking-spaces/owner/{ownerId}/revenue-report
    Spring->>Spring: Query local DB for owner's parking spots
    
    loop For each parking spot
        Spring->>Feign: Request bookings for Spot ID
        Feign->>Eureka: Resolve 'PARKING-SPOT-SERVICE' IP & Port
        Eureka-->>Feign: Return Flask Service Instance Location
        Feign->>Flask: GET /api/v1/parking/bookings/spot/{spotId}
        Flask-->>Feign: Return List of BookingDTOs
        Feign-->>Spring: Map JSON to List<BookingDTO>
    end

    Spring->>Spring: Aggregate total revenue and count
    Spring-->>Client: Return OwnerRevenueReportDTO
🌟 Key Features
Dynamic Service Discovery: Both Java and Python services register dynamically with the Eureka Server.

Spot Management & Search: Owners can register spots, and clients can query available spots based on location and vehicle type.

Reservation Engine: Real-time lifecycle management for parking spot states (AVAILABLE, RESERVED, RELEASED).

Declarative Communication: Clean, annotation-driven inter-service HTTP calls using Spring Cloud OpenFeign.

Owner Revenue Dashboard: Aggregated data generation by combining spot details from Spring Boot with booking transactions from Flask.

🚀 Setup & Running Locally
Prerequisites
JDK 17+

Maven 3.x

Python 3.10+

MySQL Server

Eureka Discovery Server running on http://localhost:8761

Step 1: Start Eureka Discovery Server
Ensure your Spring Cloud Eureka Server is running on port 8761.

Step 2: Run Python Flask Service
Bash
cd parking-spot-service
python -m venv venv
source venv/bin/activate
pip install -r requirements.txt
python app.py
Step 3: Run Spring Boot Service
Bash
cd parking-space-sevice
mvn clean install
mvn spring-boot:run
💡 Key Engineering Challenges Solved
Polyglot Integration: Bridging Spring Cloud Eureka with Python using py-eureka-client.

URL Prefix Realignment: Resolving inter-service 404 NOT FOUND Feign exceptions by matching Flask Blueprint routes (/api/v1/parking) with OpenFeign interfaces.

DTO Harmonization: Ensuring field-level mapping consistency between Python dictionaries and Java DTO objects during JSON serialization.

