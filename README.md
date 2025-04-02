# **DesignMyExperienceAPI** 🎨✨

## 📌 Overview

**DesignMyExperienceAPI** is a **scalable and modular API** that allows **clients to book activities and services** offered by **business owners**. The architecture is designed for **security**, **maintainability**, and **scalability**. It now includes **hashed password authentication** and **Ethereum-based crypto payments**.

---

## 🏗 Architecture Overview

### 🌍 Layered Architecture

The API follows a **layered architecture** to separate concerns and optimize maintainability.

---

## 📂 Project Structure

The API is organized with a **clear and modular folder structure**:

- ✅ **Separation of concerns**: Each layer has a well-defined role.  
- ✅ **Enhanced security**: DTOs prevent sensitive data exposure.  
- ✅ **Scalability**: The structure allows easy expansion of features.  
- ✅ **Security-first**: Passwords are **hashed using BCrypt**, and sensitive logic like **payment validation** is centralized and validated server-side.

---

## 🔐 Security Features

### ✅ **Password Hashing**
- All passwords are hashed with **BCrypt** before being stored in the database.
- Passwords are compared securely using a utility function to avoid timing attacks.

### ✅ **Crypto Payments Support**
- The API supports **Ethereum (Sepolia)** transactions.
- Bookings can be validated using a **transaction hash**.
- Transactions are checked to confirm:
  - ✅ Sufficient ETH value (converted from expected USD price)
  - ✅ Correct recipient wallet
  - ✅ Transaction has been mined
  - ✅ No duplicate usage

---

## 📬 Endpoints

### 🧑‍💼 User Management

- `POST /api/users/clients` – Create a new client
- `POST /api/users/business-owners` – Create a new business owner
- `POST /api/users/login` – Authenticate user (email + password)
- `GET /api/users/{id}` – Get user by ID
- `GET /api/users/clients/{id}` – Get client by ID
- `GET /api/users/business-owners/{id}` – Get business owner by ID

### 🧾 Bookings

- `POST /api/bookings/create` – Create a new booking
- `GET /api/bookings/client/{clientId}` – Get bookings by client
- `GET /api/bookings/offering/{offeringId}` – Get bookings by offering
- `GET /api/bookings/check_discount` – Check discount code

### 🧠 Offerings

- `GET /api/offerings` – Get all offerings
- `GET /api/offerings/{id}` – Get specific offering
- `GET /api/offerings/business-owner/{businessOwnerId}` – Get offerings by business owner
- `POST /api/offerings/activities` – Create a new activity
- `POST /api/offerings/services` – Create a new service
- `GET /api/offerings/activities/{id}` – Get activity by ID
- `GET /api/offerings/services/{id}` – Get service by ID

### 💸 Payments

- `POST /api/payments/validate` – Validate a booking with Ethereum transaction

---

## ⚙️ Environment Setup

- Java 17
- Spring Boot
- Gradle
- PostgreSQL
- Web3j (for Ethereum transaction validation)

---

## 📁 .gitignore Best Practices

Ensure secrets are never committed:
```
# Secrets
src/main/java/com/utopia/designmyexperience_api/validator/secrets
```

---

## 💬 Contact
For any questions or contributions, feel free to open an issue or a pull request.

---

✨ _Built with love to empower experiences._
