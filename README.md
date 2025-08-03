# Task Tracker API

A Spring Boot RESTful API for managing tasks, including creation, retrieval, status updates, and
deletion. Supports pagination and filtering.

## Features

- Create, retrieve, update, and delete tasks
- Filter tasks by status, due date, and title
- Paginated task listing
- API documentation via Swagger/OpenAPI

## Technologies

- Java
- Spring Boot
- Maven
- SQL (database integration)
- Swagger (OpenAPI)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- A SQL database (e.g., PostgreSQL, MySQL)

### Setup

1. Clone the repository: git clone <repository-url> cd <project-directory>
2. Configure your database in `src/main/resources/application.properties`.

3. Build and run the application: mvn clean install spring-boot:run

### API Endpoints

- `POST /api/v1/tasks` - Create a new task
- `GET /api/v1/tasks/{id}` - Retrieve a task by ID
- `GET /api/v1/tasks/retrieve` - List tasks (with filters and pagination)
- `PATCH /api/v1/tasks/{id}/status` - Update task status
- `DELETE /api/v1/tasks/{id}` - Delete a task

### API Documentation

Access Swagger UI at:  
`http://localhost:8080/swagger-ui.html`

## License

This project is licensed under the MIT License.
