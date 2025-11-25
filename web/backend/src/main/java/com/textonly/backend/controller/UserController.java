package com.textonly.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Simulăm o listă de utilizatori în memorie
    private final List<Map<String, String>> users = new ArrayList<>();

    // ✅ Obține toți utilizatorii
    @GetMapping
    public List<Map<String, String>> getUsers() {
        return users;
    }

    // ✅ Creează un utilizator nou
    @PostMapping
    public String createUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");

        if (username == null || username.isEmpty()) {
            return "Eroare: numele utilizatorului este gol ❌";
        }

        Map<String, String> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email != null ? email : "necunoscut");
        users.add(user);

        return "Utilizator creat: " + username;
    }

    // ✅ Șterge un utilizator după nume
    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
        boolean removed = users.removeIf(user -> user.get("username").equals(username));
        return removed ? "Utilizator șters: " + username : "Utilizatorul nu există ❌";
    }
}
