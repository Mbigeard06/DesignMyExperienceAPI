DesignMyExperienceAPI 🎨✨

📌 Overview

DesignMyExperienceAPI is a scalable and modular API that allows clients to book activities and services offered by business owners. The architecture is designed for security, maintainability, and scalability.

⸻

🏗 Architecture Overview

🌍 Layered Architecture

The API follows a layered architecture to separate concerns and optimize maintainability.


⸻

📂 Project Structure

The API is organized with a clear and modular folder structure:

✅ Separation of concerns: Each layer has a well-defined role.
✅ Enhanced security: DTOs prevent sensitive data exposure.
✅ Scalability: The structure allows easy expansion of features.

🔌 API Endpoints

Method	Endpoint	Description
POST	/api/users/login	Authenticates a user by email and password.
POST	/api/users/clients	Creates a new client user.
POST	/api/users/business-owners	Creates a new business owner user.
GET	/api/users/{id}	Retrieves a user by ID.
GET	/api/users	Retrieves a list of all users.
GET	/api/users/clients/{id}	Retrieves a client user by ID.
GET	/api/users/business-owners/{id}	Retrieves a business owner user by ID.
GET	/api/offerings	Retrieves all offerings.
GET	/api/offerings/{id}	Retrieves a specific offering by ID.
GET	/api/offerings/business-owner/{id}	Retrieves offerings by business owner ID.
GET	/api/offerings/activities/{id}	Retrieves an activity offering by ID.
GET	/api/offerings/services/{id}	Retrieves a service offering by ID.
POST	/api/offerings/activities	Creates a new activity offering.
POST	/api/offerings/services	Creates a new service offering.
GET	/api/bookings/client/{clientId}	Retrieves all bookings for a specific client.
GET	/api/bookings/offering/{offeringId}	Retrieves all bookings related to a specific offering.
POST	/api/bookings/create	Creates a booking (with attendee count) for a specific offering and client.
GET	/api/bookings/check_discount Checks if a discount code is valid
POST	/api/payments/validate-payment	Validates an Ethereum payment and confirms or deletes the booking.



⸻

✅ Swagger UI is available for API testing.

⸻

🔒 Security
	•	JWT Authentication secures all endpoints.
	•	Input validation prevents SQL injection & XSS attacks.
	•	Role-based access control (Clients vs. Business Owners vs. Admins).

⸻

