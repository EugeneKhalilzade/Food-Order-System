# Restaurant Order System

A complete backend system for restaurant order management with RESTful API endpoints for customer registration, menu management, order processing, and admin functionalities.

## Overview

The Restaurant Order System allows customers to browse the menu, place orders, and track order statuses. Restaurant staff can manage orders and update their statuses, while administrators have full access to all system functionalities.

## Technology Stack

- **Backend:** Spring Boot
- **Database:** PostgreSQL
- **Security:** Spring Security with JWT
- **Documentation:** Swagger
- **Testing:** JUnit, Mockito
- **Containerization:** Docker

## Setup and Installation

### Prerequisites

- Java 17 or higher
- Gradle
- PostgreSQL
- Git

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/44J-jottabyte/FoodOrderSystem.git
   ```

2. Configure database connection in `application.yml`:
                                 `docker-compose.yml`:

## Development Workflow

1. User registers and logs in to obtain JWT token
2. User browses menu items with optional filters
3. User creates an order with selected menu items
4. User can check order status

## API Documentation

API documentation is available via Swagger UI at `http://localhost:8080/swagger-ui/index.html` when the application is running.

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit your changes: `git commit -m 'Add feature'`
4. Push to the branch: `git push origin feature-name`
5. Submit a pull request

## License

This project is open-source and available under the MIT License. You’re free to use and modify it.
