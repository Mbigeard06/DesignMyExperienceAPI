# **DesignMyExperienceAPI** 🎨✨

## **📌 Overview**
DesignMyExperienceAPI is a **scalable and modular API** that allows **clients to book activities and services** offered by **business owners**. The architecture is designed for **security, maintainability, and scalability**.

---

## **🏗 Architecture Overview**
### **🌍 Layered Architecture**
The API follows a **layered architecture** to separate concerns and optimize maintainability.

![API Architecture](https://github.com/user-attachments/assets/0a82a76f-40d4-469f-8564-21b0298fc451)

---

## **📂 Project Structure**
The API is organized with a **clear and modular** folder structure:

✅ **Separation of concerns**: Each layer has a well-defined role.  
✅ **Enhanced security**: DTOs prevent sensitive data exposure.  
✅ **Scalability**: The structure allows easy expansion of features.  

---

## **🔌 API Endpoints**
| **Method** | **Endpoint** | **Description** |
|------------|-------------|----------------|
| `POST` | `/auth/login` | Authenticates a user and generates a JWT token. |
| `POST` | `/auth/register` | Registers a new user. |
| `GET` | `/users/{id}` | Retrieves user information (secured). |
| `GET` | `/offerings` | Fetches all available offerings. |
| `POST` | `/bookings` | Books an activity/service for a client. |
| `GET` | `/bookings/{id}` | Retrieves booking details. |

✅ **Swagger UI** is available for API testing.

---

## **🔒 Security**
- **JWT Authentication** secures all endpoints.  
- **Input validation** prevents SQL injection & XSS attacks.  
- **Role-based access control** (Clients vs. Business Owners vs. Admins).  

---

