# RideShare Backend – Spring Boot + MongoDB + JWT

## Overview

This is a mini Ride Sharing backend built using:

- Spring Boot
- MongoDB
- Spring Security with JWT
- DTOs + Validation
- Global Exception Handling

Features:

- User & Driver registration with roles (`ROLE_USER`, `ROLE_DRIVER`)
- Login with JWT token generation
- Passenger can request a ride
- Driver can view pending ride requests and accept them
- User/Driver can complete the ride
- All data stored in MongoDB

## Tech Stack

- Java
- Spring Boot (Web, Security, Validation, Data MongoDB)
- MongoDB
- JWT (JJWT)
- Maven
- Lombok

## Important Endpoints

### Auth

- `POST /api/auth/register` – Register USER or DRIVER
- `POST /api/auth/login` – Login and get JWT token

### Ride

- `POST /api/v1/rides` – (ROLE_USER) Request a ride
- `GET /api/v1/user/rides` – (ROLE_USER) View my rides
- `GET /api/v1/driver/rides/requests` – (ROLE_DRIVER) View pending ride requests
- `POST /api/v1/driver/rides/{rideId}/accept` – (ROLE_DRIVER) Accept a ride
- `POST /api/v1/rides/{rideId}/complete` – (ROLE_USER / ROLE_DRIVER) Complete a ride

## How to Run

1. Start MongoDB locally (default `mongodb://localhost:27017`)
2. In `application.yml`, Mongo URI is configured
3. Run the project:

```bash
mvn spring-boot:run
```


## Postman Collection

You can import the Postman collection from here:

➡️ **src/Postman_Collection/RideShare.postman_collection.json**

After import:
- Login first to get JWT token
- Add `Authorization: Bearer <token>` in headers to test secured APIs