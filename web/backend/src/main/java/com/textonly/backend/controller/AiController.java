package com.textonly.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*")
public class AiController {

    /**
     * Chat with AI
     * Request body: { "message": "Hello AI" }
     */
    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        
        // TODO: Integrate with actual AI service (ChatGPT, etc.)
        // For now, return a simple echo response
        String response = "AI Response: I received your message: " + message;

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("response", response);

        return ResponseEntity.ok(result);
    }
}
