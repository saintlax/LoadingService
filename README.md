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
"weightLimit": 20,
"battery": 85,
"boxStatus": "IDLE"
}

# Response
{
"id": 1,
"txref": "CKA-001",
"weightLimit": 20,
"battery": "FULL",
"state": "IDLE"
}


## Sample Errors
{
"errors": [
"txref: must not be blank",
"weightLimit: must be greater than or equal to 1"
]
}

## Load Items into a Box
# Request
POST http://localhost:8080/api/loadbox/box-002
Content-Type: application/json

{
"items": [
{
"id": 1,
"name": "Paracetamol",
"weight": 10,
"code": "ITEM001",
"boxTxref": "uty"
},
{
"id": 2,
"name": "Glucose",
"weight": 5,
"code": "ITEM002",
"boxTxref": "uty"
}
]

}

# Response 
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

## Get items
GET http://localhost:8080/api/getItems/ITEM002
# Response
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


## Get All available boxes
GET http://localhost:8080/api/getAllAvailableBoxes
# Response
[
{
"txref": "box-001",
"weightLimit": 80,
"battery": "FULL",
"state": "IDLE"
}
]


## Check battery
GET http://localhost:8080/api/checkBattery/box-002
# Response
{
"txref": "box-002",
"level": "FULL",
"meter": 85
}

# Error 
{
"error": "Box not found: box-00X"
}

