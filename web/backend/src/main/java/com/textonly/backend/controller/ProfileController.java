package com.textonly.backend.controller;

import com.textonly.backend.model.User;
import com.textonly.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get user profile
     */
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getProfile(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("username", user.getUsername());
        profile.put("email", user.getEmail());
        profile.put("displayName", user.getDisplayName());
        profile.put("profileImageUri", user.getProfileImageUri());
        profile.put("coinBalance", user.getCoinBalance());
        profile.put("walletBalance", user.getWalletBalance());

        return ResponseEntity.ok(profile);
    }

    /**
     * Update user profile
     * Request body: { "displayName": "John Doe", "profileImageUri": "..." }
     */
    @PostMapping("/profile")
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody Map<String, Object> request) {
        Long userId = Long.parseLong(request.get("userId").toString());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.containsKey("displayName")) {
            user.setDisplayName(request.get("displayName").toString());
        }
        if (request.containsKey("profileImageUri")) {
            user.setProfileImageUri(request.get("profileImageUri").toString());
        }

        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Profile updated successfully");
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    /**
     * Get user balance
     */
    @GetMapping("/balance/{userId}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> balance = new HashMap<>();
        balance.put("coinBalance", user.getCoinBalance());
        balance.put("walletBalance", user.getWalletBalance());

        return ResponseEntity.ok(balance);
    }
}
