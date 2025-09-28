# Car Rental Backend

Spring Boot backend for a simple **car rental management system**.  
Supports user registration/login, car management, and rental workflows with role-based security.

---

## 🚀 Features
- **User authentication** (Spring Security, BCrypt)
- **Role-based access** (CLIENT, ADMIN)
- **Register & login**
- **Car management** (list, availability, rental status)
- **Rental management** (create rental, approve/reject, return car)
- REST API with JSON

---

## 🛠️ Tech Stack
- **Java 17+**
- **Spring Boot 3**
- **Spring Security**
- **Spring Data JPA (Hibernate)**
- **MySQL / PostgreSQL / H2** (configurable)
- **Lombok**

---
Configure database in src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/car_rental
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


## Run the app:

./mvnw spring-boot:run
## ⚙️ Setup & Run

1. Clone repository:
   ```bash
   git clone https://github.com/yourusername/car-rental-backend.git
   cd car-rental-backend
   ```
2. Configure database in src/main/resources/application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/car_rental
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```



## 🔑 Run the app:
```
./mvnw spring-boot:run
```
