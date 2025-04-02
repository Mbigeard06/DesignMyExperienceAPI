# **DesignMyExperienceAPI** 🎨✨

## 📌 Overview

**DesignMyExperienceAPI** is a **scalable and modular API** that allows **clients to book activities and services** offered by **business owners**. The architecture is designed for **security**, **maintainability**, and **scalability**.

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

---

## 🔐 Security & Authentication

- 🔒 **Password Hashing**: All user passwords are hashed using **BCrypt** before being stored in the database. This ensures that even if the DB is compromised, original passwords remain secure.
- 🔄 **Password Comparison**: During login, input passwords are hashed and then compared with the stored hashed version.
- 🧾 **Crypto Payments**: Users can book services or activities by paying in **Ethereum**. The API checks the blockchain (Sepolia network) to verify:
  - the amount sent (converted from expected USD amount),
  - the receiver address,
  - and ensures the transaction is confirmed and not reused.

---

## 📧 Email SMTP Integration

- ✅ **Account Confirmation**: When a new client or business owner is created, a confirmation email is sent via **SMTP**.
- 🛡️ **Secure Sending**: Uses authenticated SMTP session (via Gmail or custom mail provider).
- 🔄 **Modular Design**: Email sending logic is handled by a dedicated **EmailService**.

---

## 🚀 API Endpoints

### 👤 Users
- `GET /api/users/{id}` – Get user by ID
- `GET /api/users` – Get all users
- `POST /api/users/clients` – Create a new client
- `POST /api/users/business-owners` – Create a new business owner
- `POST /api/users/login` – Login with email and password

### 🧾 Bookings
- `POST /api/bookings/create` – Create a new booking (specify offering ID, client ID, etc.)
- `GET /api/bookings/client/{clientId}` – Get all bookings by a client
- `GET /api/bookings/offering/{offeringId}` – Get all bookings for an offering
- `GET /api/bookings/check_discount` – Check if a discount code is valid for a given offering
- `GET /api/bookings/checkBooking` – Check if a discount code is valid for a given offering

### 💸 Payments
- `POST /api/payments/validate` – Validate an Ethereum payment by checking:
  - Transaction hash
  - Amount sent
  - If the booking exists and hasn’t been paid before

### 🎭 Offerings
- `GET /api/offerings/business-owner/{id}` – Get offerings by business owner
- `GET /api/offerings/{id}` – Get a specific offering
- `GET /api/offerings` – Get all offerings
- `POST /api/offerings/activities` – Create a new activity
- `POST /api/offerings/services` – Create a new service
- `DELETE /api/offerings/delete_offering/{id}` – Delete an offering

---

## 🛠 Technologies

- Java 17
- Spring Boot
- PostgreSQL
- Web3j (Ethereum blockchain interaction)
- JavaMail (SMTP)
- BCrypt (Password security)

---

## 🧪 Coming Soon

- 🔐 Email verification token system
- 📊 Booking statistics for business owners
- 🌍 Frontend interface (React or Android)

---

## 🧑‍💻 Authors

- Developed by **Mateo Bigeard Dasen**

---

> "Design your experience, securely." 🛡️🎉
