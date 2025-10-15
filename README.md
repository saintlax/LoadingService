# Loading Service (Spring Boot)

## Overview
This project is a RESTful service built with Spring Boot to manage Boxes and Items for loading and delivery operations.

## Features
- Add boxes with status and weight limit
- Load items into available boxes
- Prevent loading when battery level is below 25%
- Check box availability based on status and battery
- Monitor and report box battery status
-️ Prevent duplicate item codes

## Requirements
Requires Java 17+ and Maven.

## Build and Run
# Clone the repository
git clone https://github.com/saintlax/LoadingService.git
cd LoadingService

## Build the project
mvn clean package

## Run the application
mvn spring-boot:run

## Sample request
# Create box
POST http://localhost:8080/api/addbox
Content-Type: application/json

{
"txref": "CKA-001",
"weightLimit": 50,
"battery": 75,
"boxStatus": "IDLE"
}

# Response
{
"id": 1,
"txref": "CKA-001",
"weightLimit": 50,
"battery": 75,
"boxStatus": "IDLE"
}

## Load Items into a Box
# Request
POST http://localhost:8080/api/loadbox/CKA-001
Content-Type: application/json

[
{
"name": "Paracetamol",
"weight": 10,
"code": "ITEM001"
},
{
"name": "Glucose",
"weight": 5,
"code": "ITEM002"
}
]

# Response 
[
{
"id": 1,
"name": "Paracetamol",
"weight": 10,
"code": "ITEM001",
"txref": "CKA-001"
},
{
"id": 2,
"name": "Glucose",
"weight": 5,
"code": "ITEM002",
"txref": "CKA-001"
}
]



