# Loading Service (Spring Boot)

## Overview
REST service to manage Boxes and Items for loading & delivery.
- Allow boxes to be added according to status and weight
- Allows items to be loaded into boxes
- Box cannot be loaded when battery < 25%
- Checks for availble boxes
- check battery status

## Build
Requires Java 17+ and Maven.

```bash
mvn clean package
