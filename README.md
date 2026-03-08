# Finance Tracker — Backend
> API REST para gestión de finanzas personales con autenticación JWT y despliegue en AWS.

---

## 🇲🇽 Español

### Descripción
API REST desarrollada con Spring Boot que permite a los usuarios registrar y gestionar sus movimientos financieros (ingresos y egresos). Implementa autenticación segura con JWT, arquitectura en capas (Controller / Service / Repository) y está desplegada en AWS EC2 con base de datos en RDS PostgreSQL. El frontend está construido con Angular y Angular Material.

### Tecnologías
- Java 21
- Spring Boot 4
- Spring Security + JWT
- PostgreSQL (AWS RDS)
- Hibernate / JPA
- Maven
- AWS EC2

### Características
- Registro e inicio de sesión con JWT
- Token con expiración configurable vía `application.properties`
- Filtro HTTP personalizado (`JwtAuthFilter`) que intercepta y valida cada petición
- Validación del token por email + verificación de expiración en cada request
- CRUD completo de transacciones
- Dashboard con total de ingresos, egresos y balance del mes
- Cada usuario solo ve sus propias transacciones
- Arquitectura en capas: Controller → Service → Repository
- Manejo global de excepciones
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

# 3. Configurar application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_tracker
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD

# 4. Correr el proyecto
./mvnw spring-boot:run
```

### Endpoints principales
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Registrar usuario | No |
| POST | /api/auth/login | Iniciar sesión | No |
| GET | /api/transaction | Listar transacciones | Sí |
| POST | /api/transaction | Crear transacción | Sí |
| PUT | /api/transaction/{id} | Actualizar transacción | Sí |
| DELETE | /api/transaction/{id} | Eliminar transacción | Sí |
| GET | /api/transaction/dashboard | Ver resumen del mes | Sí |

### Frontend relacionado
[finance-tracker-frontend](https://github.com/Chiripi0rca/finance-tracker-frontend)

### Autor
Ricardo Ramos Puga — [GitHub](https://github.com/Chiripi0rca)

---

## 🇺🇸 English

### Description
REST API built with Spring Boot for managing personal finances (income and expenses). Features JWT authentication, layered architecture (Controller / Service / Repository), and is deployed on AWS EC2 with a PostgreSQL database on RDS. The frontend is built with Angular and Angular Material.

### Tech Stack
- Java 21
- Spring Boot 4
- Spring Security + JWT
- PostgreSQL (AWS RDS)
- Hibernate / JPA
- Maven
- AWS EC2

### Features
- User registration and login with JWT
- Configurable token expiration via `application.properties`
- Custom HTTP filter (`JwtAuthFilter`) that intercepts and validates every request
- Token validation by email + expiration check on each request
- Full CRUD for transactions
- Monthly dashboard with income, expenses and balance summary
- Each user only sees their own transactions
- Layered architecture: Controller → Service → Repository
- Global exception handling
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

# 3. Configure application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_tracker
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

# 4. Run the project
./mvnw spring-boot:run
```

### Main Endpoints
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Register user | No |
| POST | /api/auth/login | Login | No |
| GET | /api/transaction | List transactions | Yes |
| POST | /api/transaction | Create transaction | Yes |
| PUT | /api/transaction/{id} | Update transaction | Yes |
| DELETE | /api/transaction/{id} | Delete transaction | Yes |
| GET | /api/transaction/dashboard | Monthly summary | Yes |

### Related Frontend
[finance-tracker-frontend](https://github.com/Chiripi0rca/finance-tracker-frontend)

### Author
Ricardo Ramos Puga — [GitHub](https://github.com/Chiripi0rca)
