# Finance Tracker — Backend
> API REST para gestión de finanzas personales con autenticación JWT y despliegue en AWS.

---

## 🇲🇽 Español

### Descripción
API REST desarrollada con Spring Boot que permite a los usuarios registrar y gestionar sus movimientos financieros (ingresos y egresos). Implementa autenticación segura con JWT + Refresh Tokens, arquitectura en capas (Controller / Service / Repository), paginación, filtros y exportación de datos. Desplegada en AWS EC2 con base de datos en RDS PostgreSQL. El frontend está construido con Angular y Angular Material.

### Tecnologías
- Java 21
- Spring Boot 4
- Spring Security + JWT + Refresh Tokens
- PostgreSQL (AWS RDS)
- Hibernate / JPA
- Maven
- AWS EC2
- Springdoc OpenAPI (Swagger UI)
- JUnit 5 + Mockito

### Características
- Registro e inicio de sesión con JWT
- Refresh Tokens almacenados en DB con expiración de 7 días
- Token con expiración configurable vía variables de entorno
- Filtro HTTP personalizado (`JwtAuthFilter`) que intercepta y valida cada petición
- CRUD completo de transacciones
- Filtros opcionales por categoría (`TipoCategoria` enum) y mes
- Paginación con `PageResponseDTO` (page, size, totalPages, totalElements)
- Dashboard mensual con total de ingresos, egresos y balance
- Exportación de transacciones a CSV
- Cada usuario solo ve sus propias transacciones
- Arquitectura en capas: Controller → Service → Repository
- Validaciones con `@Valid`, `@Email`, `@Positive` en DTOs
- Manejo global de excepciones
- Credenciales en variables de entorno (sin valores hardcodeados)
- Documentación interactiva con Swagger UI en `/swagger-ui/index.html`
- Tests unitarios con JUnit 5 + Mockito
- CORS configurado para frontend Angular

### Instalación local
**Requisitos:**
- Java 21
- Maven
- PostgreSQL corriendo en localhost:5432

**Pasos:**
```bash
# 1. Clonar el repositorio
git clone https://github.com/Chiripi0rca/finance-tracker-backend.git
cd finance-tracker-backend

# 2. Crear la base de datos
psql -U postgres -c "CREATE DATABASE finance_tracker;"

# 3. Crear application-local.properties con tus variables
DB_URL=jdbc:postgresql://localhost:5432/finance_tracker
DB_USER=postgres
DB_PASSWORD=TU_PASSWORD
JWT_SECRET=TU_CLAVE_SECRETA
JWT_EXPIRATION=86400000

# 4. Correr el proyecto con perfil local
./mvnw spring-boot:run -Dspring.profiles.active=local
```

### Endpoints principales
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Registrar usuario | No |
| POST | /api/auth/login | Iniciar sesión | No |
| POST | /api/auth/refresh | Renovar access token | No |
| GET | /api/transaction | Listar transacciones (filtros + paginación) | Sí |
| POST | /api/transaction | Crear transacción | Sí |
| PUT | /api/transaction/{id} | Actualizar transacción | Sí |
| DELETE | /api/transaction/{id} | Eliminar transacción | Sí |
| GET | /api/transaction/dashboard | Ver resumen del mes | Sí |
| GET | /api/transaction/export/csv | Exportar transacciones a CSV | Sí |

### Parámetros de listado
```
GET /api/transaction?page=0&size=10&categoria=COMIDA&mes=2026-03-01
```
- `page` — número de página (default: 0)
- `size` — registros por página (default: 10, máximo: 50)
- `categoria` — COMIDA, RENTA, ENTRETENIMIENTO, SALUD, NOMINA, TRANSPORTE, OTROS
- `mes` — formato `yyyy-MM-dd` (primer día del mes)

### Frontend relacionado
[finance-tracker-frontend](https://github.com/Chiripi0rca/finance-tracker-frontend)

### Autor
Ricardo Ramos Puga — [GitHub](https://github.com/Chiripi0rca)

---

## 🇺🇸 English

### Description
REST API built with Spring Boot for managing personal finances (income and expenses). Features JWT authentication with Refresh Tokens, layered architecture (Controller / Service / Repository), pagination, filters, and data export. Deployed on AWS EC2 with a PostgreSQL database on RDS. The frontend is built with Angular and Angular Material.

### Tech Stack
- Java 21
- Spring Boot 4
- Spring Security + JWT + Refresh Tokens
- PostgreSQL (AWS RDS)
- Hibernate / JPA
- Maven
- AWS EC2
- Springdoc OpenAPI (Swagger UI)
- JUnit 5 + Mockito

### Features
- User registration and login with JWT
- Refresh Tokens stored in DB with 7-day expiration
- Configurable token expiration via environment variables
- Custom HTTP filter (`JwtAuthFilter`) that intercepts and validates every request
- Full CRUD for transactions
- Optional filters by category (`TipoCategoria` enum) and month
- Pagination with `PageResponseDTO` (page, size, totalPages, totalElements)
- Monthly dashboard with income, expenses and balance summary
- CSV export for all user transactions
- Each user only sees their own transactions
- Layered architecture: Controller → Service → Repository
- DTO validations with `@Valid`, `@Email`, `@Positive`
- Global exception handling
- Credentials stored in environment variables (no hardcoded secrets)
- Interactive API documentation with Swagger UI at `/swagger-ui/index.html`
- Unit tests with JUnit 5 + Mockito
- CORS configured for Angular frontend

### Local Setup
**Requirements:**
- Java 21
- Maven
- PostgreSQL running on localhost:5432

**Steps:**
```bash
# 1. Clone the repository
git clone https://github.com/Chiripi0rca/finance-tracker-backend.git
cd finance-tracker-backend

# 2. Create the database
psql -U postgres -c "CREATE DATABASE finance_tracker;"

# 3. Create application-local.properties with your variables
DB_URL=jdbc:postgresql://localhost:5432/finance_tracker
DB_USER=postgres
DB_PASSWORD=YOUR_PASSWORD
JWT_SECRET=YOUR_SECRET_KEY
JWT_EXPIRATION=86400000

# 4. Run the project with local profile
./mvnw spring-boot:run -Dspring.profiles.active=local
```

### Main Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Register user | No |
| POST | /api/auth/login | Login | No |
| POST | /api/auth/refresh | Refresh access token | No |
| GET | /api/transaction | List transactions (filters + pagination) | Yes |
| POST | /api/transaction | Create transaction | Yes |
| PUT | /api/transaction/{id} | Update transaction | Yes |
| DELETE | /api/transaction/{id} | Delete transaction | Yes |
| GET | /api/transaction/dashboard | Monthly summary | Yes |
| GET | /api/transaction/export/csv | Export transactions to CSV | Yes |

### Query Parameters
```
GET /api/transaction?page=0&size=10&categoria=COMIDA&mes=2026-03-01
```
- `page` — page number (default: 0)
- `size` — records per page (default: 10, max: 50)
- `categoria` — COMIDA, RENTA, ENTRETENIMIENTO, SALUD, NOMINA, TRANSPORTE, OTROS
- `mes` — format `yyyy-MM-dd` (first day of the month)

### Related Frontend
[finance-tracker-frontend](https://github.com/Chiripi0rca/finance-tracker-frontend)

### Author
Ricardo Ramos Puga — [GitHub](https://github.com/Chiripi0rca)