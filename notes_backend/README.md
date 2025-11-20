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

Fast assemble (no tests) for preview environments:
```
./gradlew assembleOnly
```

If CI needs a faster unit test sweep without web-layer tests:
```
./gradlew clean test -PquickUnit
```
or select specific tests:
```
./gradlew test --tests '*NoteServiceTest'
```

## Run

Recommended for preview (no tests and no devtools/live-reload blocking):
```
./gradlew runFast
```

If port 3001 is in use in your environment (common in CI/preview behind a proxy), run on a random free port:
```
SERVER_PORT=0 ./gradlew runFast
```
or set a specific port:
```
SERVER_PORT=3011 ./gradlew runFast
```

Standard:
```
./gradlew bootRun
```

App (default): http://localhost:3001  
Docs: http://localhost:3001/swagger-ui.html  
H2: http://localhost:3001/h2-console (JDBC URL: jdbc:h2:mem:notesdb)

### Quick health checks

- Health: `curl -i http://localhost:3001/health` should return `200 OK` with body `OK`.
- Notes list: `curl -i http://localhost:3001/api/notes` should return `200 OK` with a paginated JSON.

### Troubleshooting: Port already in use

If startup fails with:
```
APPLICATION FAILED TO START
Web server failed to start. Port 3001 was already in use.
```
Identify and stop the process on 3001:
```
lsof -i :3001 -sTCP:LISTEN -n -P
kill -9 <PID>
```
Alternatively, override the port:
```
SERVER_PORT=0 ./gradlew bootRun
```
or set a specific port:
```
SERVER_PORT=3011 ./gradlew bootRun
```

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
