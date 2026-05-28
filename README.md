# cap-homework
Lets create simple TODO WEB application which will enable you to handle your own private todo list. Here are the requirements
- The application will expose 3 apis to get all todos, search by id and delete by id
- Todos contains id(auto generated), title, status(OPEN,CLOSED) and time of creation.
- Payload of the requests/response is in JSON format
- Api design its up to you. Please consider best practice though.
- Todo's must be persisted in database of your choice, integrated with hibernate
- Your application code must be fully covered with junit tests
- Framework usage its up to you but try to limit the number of dependencies to bare minimum. (Prefered framework is quarkus ;)

Bonus tasks
- Configure swagger ui for your web app or (if you are familiar with frontend development) write simple ui(angular prefered) for exposed 3 apis
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# Cap Homework - Todo API

Spring Boot REST API for Todo management with H2 database, Hibernate/JPA, validation, exception handling, Swagger/OpenAPI documentation, and JUnit testing.

---

## Features

* Create Todo
* Get All Todos
* Get Todo By Id
* Delete Todo
* DTO Separation
* Global Exception Handling
* Request Validation
* H2 In-Memory Database
* Hibernate/JPA Integration
* Swagger/OpenAPI Documentation
* Unit Testing using JUnit & Mockito

---

## Tech Stack

* Java 24
* Spring Boot 3
* Spring Data JPA
* H2 Database
* Maven
* Lombok
* Swagger / OpenAPI
* JUnit 5
* Mockito

---

## API Endpoints

### Create Todo

POST `/todos`

Request:

```json
{
  "title": "Learn Spring Boot"
}
```

Response:

```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "status": "OPEN",
  "createdAt": "2026-05-29T04:20:00"
}
```

---

### Get All Todos

GET `/todos`

---

### Get Todo By Id

GET `/todos/{id}`

---

### Delete Todo

DELETE `/todos/{id}`

---

## Validation

* Title cannot be blank

Example validation response:

```json
{
  "title": "Title is required"
}
```

---

## Exception Handling

Example:

```json
{
  "message": "Todo not found"
}
```

---

## Swagger UI

Swagger UI available at:

http://localhost:8082/swagger-ui/index.html

---

## H2 Database Console

H2 Console available at:

http://localhost:8082/h2-console

Use:

* JDBC URL: `jdbc:h2:mem:testdb`
* Username: `sa`
* Password: empty

---

## Run Application

Using Maven Wrapper:

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/Mac:

```bash
./mvnw spring-boot:run
```

---

## Run Tests

```bash
mvn test
```

---

## Project Structure

```text
controller   -> REST APIs
service      -> Business logic
repository   -> Database access
entity       -> JPA entities
dto          -> Request/Response DTOs
exception    -> Global exception handling
test         -> Unit & controller tests
```
