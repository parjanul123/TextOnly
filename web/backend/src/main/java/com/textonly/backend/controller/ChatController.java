package com.textonly.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final List<String> messages = new ArrayList<>();

    // ✅ Primește toate mesajele
    @GetMapping("/messages")
    public List<String> getMessages() {
        return messages;
    }

    // ✅ Trimite un mesaj nou
    @PostMapping("/send")
    public String sendMessage(@RequestBody Map<String, String> body) {
        String text = body.get("message");
        if (text == null || text.isEmpty()) {
            return "Eroare: mesajul este gol ❌";
        }
        messages.add(text);
        return "Mesaj trimis: " + text;
    }

    // ✅ Șterge toate mesajele (test / curățare)
    @DeleteMapping("/clear")
    public String clearMessages() {
        messages.clear();
        return "Toate mesajele au fost șterse 🗑️";
    }
}
