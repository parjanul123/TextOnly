
package com.textonly.backend.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.textonly.backend.model.User;
import com.textonly.backend.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // permite accesul și din Android / web
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // GET /api/users/{id}/wallet - obține soldul portofelului
    @GetMapping("/{id}/wallet")
    public Double getWalletBalance(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? user.getWalletBalance() : null;
    }

    // POST /api/users/{id}/wallet/add - alimentează portofelul
    @PostMapping("/{id}/wallet/add")
    public User addToWallet(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && body.containsKey("amount")) {
            user.setWalletBalance(user.getWalletBalance() + body.get("amount"));
            userRepository.save(user);
        }
        return user;
    }

    // POST /api/users/{id}/wallet/consume - consumă din portofel
    @PostMapping("/{id}/wallet/consume")
    public User consumeFromWallet(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && body.containsKey("amount")) {
            double newBalance = user.getWalletBalance() - body.get("amount");
            user.setWalletBalance(Math.max(0.0, newBalance));
            userRepository.save(user);
        }
        return user;
    }

    // PATCH /api/users/{id}/displayName
    @PatchMapping("/{id}/displayName")
    public User updateDisplayName(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && body.containsKey("displayName")) {
            user.setDisplayName(body.get("displayName"));
            userRepository.save(user);
        }
        return user;
    }

    // PATCH /api/users/{id}/profileImage
    @PatchMapping("/{id}/profileImage")
    public User updateProfileImage(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && body.containsKey("profileImageUri")) {
            user.setProfileImageUri(body.get("profileImageUri"));
            userRepository.save(user);
        }
        return user;
    }
}
