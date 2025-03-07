package com.utopia.designmyexperience_api.controller;

import com.utopia.designmyexperience_api.model.User;
import com.utopia.designmyexperience_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        User user = userService.getUserById(id);
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

}