package com.textonly.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.textonly.backend.model.User;
import com.textonly.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Simulare login prin QR (mock)
    @PostMapping("/login")
    public Map<String, Object> loginViaQR(@RequestBody Map<String, String> body) {
        String qrToken = body.get("token");

        Map<String, Object> response = new HashMap<>();
        if ("123456".equals(qrToken)) {
            response.put("status", "success");
            response.put("message", "Autentificare reușită");
            response.put("userId", 1);
        } else {
            response.put("status", "error");
            response.put("message", "Token invalid");
        }
        return response;
    }
}
