package com.utopia.designmyexperience_api.controller;

import com.utopia.designmyexperience_api.dto.CreateBusinessOwnerRequest;
import com.utopia.designmyexperience_api.dto.CreateClientRequest;
import com.utopia.designmyexperience_api.dto.LoginRequest;
import com.utopia.designmyexperience_api.model.User;
import com.utopia.designmyexperience_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get a user by ID.
     * @param id The user ID.
     * @return The user if found.
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        User user = userService.getUser(id);
        return user;
    }

    /**
     * Get all users.
     * @return A list of all users.
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            // Log l'erreur pour debug
            e.printStackTrace();

            // Retourne une réponse JSON avec un statut HTTP 500 et le message d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "An error occurred while fetching users",
                            "details", e.getMessage()
                    ));
        }
    }

    /**
     * Get a business owner by ID.
     * @param id The user ID.
     * @return The user if found and is a business owner.
     */
    @GetMapping("/business-owners/{id}")
    public ResponseEntity<?> getBusinessOwner(@PathVariable int id) {
        try {
            User user = userService.getBusinessOwner(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Business owner not found or user is not a business owner"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "An error occurred while fetching business owner",
                            "details", e.getMessage()
                    ));
        }
    }

    /**
     * Get a client by ID.
     * @param id The user ID.
     * @return The user if found and is a client.
     */
    @GetMapping("/clients/{id}")
    public ResponseEntity<?> getClient(@PathVariable int id, String hasedPassword) {
        try {
            User user = userService.getClient(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Client not found or user is not a client"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "An error occurred while fetching the client",
                            "details", e.getMessage()
                    ));
        }
    }

    /**
     * Create a new client.
     * @param request The request containing the client and hashed password.
     * @return Response with user ID or error.
     */
    @PostMapping("/clients")
    public ResponseEntity<?> createClient(@RequestBody CreateClientRequest request) {
        try {
            int userId = userService.createClient(request.getClient(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("userId", userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create client", "details", e.getMessage()));
        }
    }

    /**
     * Create a new business owner.
     * @param request The request containing the business owner and hashed password.
     * @return Response with user ID or error.
     */
    @PostMapping("/business-owners")
    public ResponseEntity<?> createBusinessOwner(@RequestBody CreateBusinessOwnerRequest request) {
        try {
            int userId = userService.createBusinessOwner(request.getBusinessOwner(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("userId", userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create business owner", "details", e.getMessage()));
        }
    }

    /**
     * Authenticate a user with email and password.
     * @param request The login request with email and password.
     * @return The authenticated user if credentials are correct.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.checkCredential(request.getEmail(), request.getPassword());

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid email or password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred during login", "details", e.getMessage()));
        }
    }
}