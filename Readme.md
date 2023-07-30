
# JwtSpringBoot Project

## Introduction

JwtSpringBoot is a **simple project** that demonstrates how to implement **JSON Web Tokens (JWT) authentication** in a Spring Boot application. This project aims to provide a basic setup and guidelines for securing APIs using JWT-based authentication.

## Features

- User registration and login endpoints with JWT token generation
- Secured API endpoints using JWT token authentication
- Error handling and custom exception responses
- Basic user information retrieval
- Sample APIs to showcase authentication and authorization

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JSON Web Tokens (JWT)
- Maven
- Postman

## Setup

To run the JwtSpringBoot project locally, follow these steps:

1. **Clone the repository:**

   ```shell
   git clone https://github.com/rupeshgh/JwtSpringBoot.git

2. **Open up Project and Use Maven to build**
     ```shell
   mvn clean install


## Endpoints

  -  **POST /login** or **/auth** : Authenticate a user and receive a JWT token in response.
  

  -  **GET /user**: requires JWT token + user role.

  - **GET /admin**: requires JWT token + admin role.

