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

## 📂 Project Structure

Banking-System/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/example/bankingsystem/
│ │ │ ├── controller/
│ │ │ ├── entity/
│ │ │ ├── repository/
│ │ │ ├── service/
│ │ │ └── BankingSystemApplication.java
│ │ └── resources/
│ │ ├── application.properties
│ │ └── static/
│ │ └── templates/
│ └── test/
├── pom.xml
└── README.md


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



