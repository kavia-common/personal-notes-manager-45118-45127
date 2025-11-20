# Notes Backend

Spring Boot REST API for a personal notes manager.

- Runs on port 3001
- H2 in-memory database (no external setup)
- CRUD endpoints under /api/notes
- Pagination, sorting (title, createdAt, updatedAt), search via q
- Global validation/error handling
- CORS enabled for all origins (development)
- Swagger UI at /swagger-ui.html
- H2 console at /h2-console

## Build

Standard build:
```
./gradlew clean build
```

If CI needs a faster preview without integration-like tests, use:
```
./gradlew clean test -PquickUnit
```

Where `-PquickUnit` is interpreted by CI to only select fast tests. Currently all tests are fairly light, but if build timeouts occur, consider limiting to specific packages or tags via:
```
./gradlew test --tests '*NoteServiceTest'
```

## Run

```
./gradlew bootRun
```

App: http://localhost:3001  
Docs: http://localhost:3001/swagger-ui.html  
H2: http://localhost:3001/h2-console (JDBC URL: jdbc:h2:mem:notesdb)

## Endpoints

- POST /api/notes
- GET /api/notes
- GET /api/notes/{id}
- PUT /api/notes/{id}
- DELETE /api/notes/{id}

### Pagination and sorting

GET /api/notes?page=0&size=10&sort=createdAt,desc  
Allowed sort fields: title, createdAt, updatedAt

### Search

GET /api/notes?q=milk

## cURL examples

Create:
```
curl -X POST http://localhost:3001/api/notes \
 -H "Content-Type: application/json" \
 -d '{"title":"Grocery list","content":"Buy milk","tags":["personal","todo"]}'
```

List:
```
curl "http://localhost:3001/api/notes?page=0&size=5&sort=createdAt,desc"
```

Get by id:
```
curl http://localhost:3001/api/notes/1
```

Update:
```
curl -X PUT http://localhost:3001/api/notes/1 \
 -H "Content-Type: application/json" \
 -d '{"title":"Updated title","content":"Updated","tags":["work"]}'
```

Delete:
```
curl -X DELETE http://localhost:3001/api/notes/1 -i
```
