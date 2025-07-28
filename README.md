# 💳 Banking System

A simple full-stack Banking System application built using **Java Spring Boot** for the backend. The project supports account management, user registration/login, and basic banking transactions.

## 🚀 Features

- ✅ User Registration and Login
- ✅ Create Bank Account
- ✅ Deposit and Withdraw Money
- ✅ Transfer Funds Between Accounts
- ✅ View Transaction History
- ✅ Profile Management

## 🛠️ Tech Stack

| Layer        | Technology        |
|--------------|-------------------|
| Backend      | Java, Spring Boot |
| Database     | MySQL             |
| Frontend     | HTML, CSS, JS     |
| IDE          | Eclipse           |
| Build Tool   | Maven             |


2. Open in Eclipse or IntelliJ
Import as a Maven project.

Let Maven resolve all dependencies.

3. Build the Project
Run the project using:

bash
Copy code
mvn clean install
Or using the Spring Boot plugin:

bash
Copy code
mvn spring-boot:run
🗄️ Database Setup
1. Open MySQL and run:
sql
Copy code
CREATE DATABASE bankingdb;
spring.application.name=BankingSystem
spring.datasource.url=jdbc:mysql://localhost:3306/bankingsystem
spring.datasource.username=root
spring.datasource.password=Swami@123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update 
# Use 'update' for development, 'none' for production
spring.jpa.show-sql=true
spring.thymeleaf.cache=false 
# For development
server.error.include-message=always
server.error.include-exception=true
server.error.include-stacktrace=always

▶️ How to Run
Step-by-step:
Start MySQL Server.

Open Eclipse or IntelliJ.

Run BankingSystemApplication.java as Java Application or Spring Boot App.

Access the application:

arduino
Copy code
http://localhost:8080/

📬 API Endpoints

| Method | Endpoint             | Description             |
| ------ | -------------------- | ----------------------- |
| POST   | `/register`          | Register a new user     |
| POST   | `/login`             | Authenticate user       |
| GET    | `/accounts/{id}`     | Get account by ID       |
| POST   | `/accounts/create`   | Create new account      |
| POST   | `/accounts/transfer` | Transfer funds          |
| GET    | `/transactions/{id}` | Get transaction history |
| PUT    | `/user/update`       | Update user profile     |


✍️ 👨‍💻 Author
Pramod Patil



