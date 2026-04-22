# Car Selling REST API

Spring Boot REST API for car selling with JWT auth, role-based access, PostgreSQL persistence, and Swagger docs.

## Tech Stack
- Spring Boot 3
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- Lombok
- Springdoc OpenAPI
- Maven

## Features
- Auth: register/login with JWT
- Roles: `USER`, `SELLER`
- Car listing CRUD with owner-only update/delete
- Favorites, messages, bookings, reviews
- Pagination and search filters for cars
- Global exception handling and request validation
- Swagger UI with Bearer token support

## API Docs
- Swagger UI: `http://localhost:8282/swagger-ui.html`

## Configuration
Set PostgreSQL and JWT values in `src/main/resources/application.properties`.

Default local config included:
- DB URL: `jdbc:postgresql://localhost:5432/postgres`
- Username: `postgres`
- Password: `1234`

You can override DB settings with environment variables:
- `DB_URL` or `JDBC_DATABASE_URL`
- `DB_HOST`, `DB_PORT`, `DB_NAME`
- `DB_USERNAME` or `DB_USER`
- `DB_PASSWORD` or `DB_PASS`

JWT/CORS environment variables:
- `APP_JWT_SECRET` (use a strong Base64-encoded secret in production)
- `APP_JWT_EXPIRATION_MS`
- `APP_CORS_ALLOWED_ORIGIN_PATTERNS`

Configure CORS allowed origins (comma-separated) with:
- `app.cors.allowed-origin-patterns`

If you want a dedicated database name like `car_db`, create it first and set:
- `DB_URL=jdbc:postgresql://localhost:5432/car_db`

## Run
```bash
mvn spring-boot:run
```

Or with Gradle wrapper:
```bash
./gradlew bootRun
```

## Test
```bash
mvn test
```

## Main Endpoints
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/cars` (SELLER)
- `GET /api/cars`
- `GET /api/cars/{id}`
- `PUT /api/cars/{id}` (owner)
- `DELETE /api/cars/{id}` (owner)
- `GET /api/cars/search?brand=&priceMin=&priceMax=&year=`
- `POST /api/favorites/{carId}`
- `DELETE /api/favorites/{carId}`
- `GET /api/favorites`
- `POST /api/messages`
- `GET /api/messages/conversation/{userId}`
- `POST /api/bookings`
- `GET /api/bookings/my`
- `PUT /api/bookings/{id}/status`
- `POST /api/reviews`
- `GET /api/reviews/seller/{sellerId}`

## JWT Usage
- Protected endpoints accept `Authorization` header in either format:
  - `Bearer <JWT_TOKEN>`
  - `<JWT_TOKEN>`
- `/api/auth/register` and `/api/auth/login` remain public and do not require a token.

## Deploy on Render
This repo includes a Render Blueprint file: `render.yaml`.
The web service is configured with `env: docker`.

1. Push this repository to GitHub.
2. In Render, choose **New +** -> **Blueprint**.
3. Connect the repo and deploy.
4. Render will create:
   - Web service: `car-api`
   - PostgreSQL database: `car-postgres`

After deploy, set frontend origin(s) in `APP_CORS_ALLOWED_ORIGIN_PATTERNS` (comma-separated), for example:
- `https://your-frontend.onrender.com,http://localhost:5500`

Useful checks after deployment:
- `https://<your-api>.onrender.com/swagger-ui.html`
- `https://<your-api>.onrender.com/v3/api-docs`

Optional local Docker smoke test:
```bash
docker build -t car-api .
docker run -p 8282:8080 car-api
```

