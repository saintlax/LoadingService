# 📦 Loading Service (Spring Boot)

##  Overview
This project is a **RESTful API** built with **Spring Boot** to manage **Boxes** and **Items** for loading and delivery operations.  
It enforces weight limits, battery thresholds, and state validations to ensure smooth logistics operations.

---

## ✨ Features
-  Add boxes with status and weight limit
-  Load items into available boxes
-  Prevent loading when battery level is below **25%**
-  Check box availability based on status and battery
-  Prevent duplicate item codes

---

## Requirements
-  Java 17+
-  Apache Maven

---

##  Build & Run

```bash
# Clone the repository
git clone https://github.com/saintlax/LoadingService.git
cd LoadingService

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

Application will start at: **http://localhost:8080**

---

##  API Endpoints

###  Create a Box
**POST** `http://localhost:8080/api/addbox`  
**Content-Type:** `application/json`

####  Request
```json
{
  "txref": "CKA-001",
  "weightLimit": 20,
  "battery": 85,
  "boxStatus": "IDLE"
}
```

####  Response
```json
{
  "id": 1,
  "txref": "CKA-001",
  "weightLimit": 20,
  "battery": "FULL",
  "state": "IDLE"
}
```

#### ️ Validation Error
```json
{
  "errors": [
    "txref: must not be blank",
    "weightLimit: must be greater than or equal to 1"
  ]
}
```

---

###  Load Items into a Box
**POST** `http://localhost:8080/api/loadbox/box-002`  
**Content-Type:** `application/json`

####  Request
```json
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
```

####  Response
```json
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
```

---

###  Get Items for a Box
**GET** `http://localhost:8080/api/getItems/ITEM002`

####  Response
```json
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
```

---

###  Get All Available Boxes
**GET** `http://localhost:8080/api/getAllAvailableBoxes`

####  Response
```json
[
  {
    "txref": "box-001",
    "weightLimit": 80,
    "battery": "FULL",
    "state": "IDLE"
  }
]
```

---

###  Check Box Battery Level
**GET** `http://localhost:8080/api/checkBattery/box-002`

####  Response
```json
{
  "txref": "box-002",
  "level": "FULL",
  "meter": 85
}
```

#### ️ Error Example
```json
{
  "error": "Box not found for box-00X"
}
```

---

##  Notes
- Boxes **cannot** be loaded if:
    - Battery is **below 25%**
    - Total item weight exceeds the box weight limit.
- Item codes must be **unique**.

---

## 🧑‍💻 Author
**Chika Anthony Enemuo**  
📎 GitHub: [https://github.com/saintlax](https://github.com/saintlax)
