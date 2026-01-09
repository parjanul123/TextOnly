package com.messenger.controller;

import com.messenger.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileWebSocketController {
    @MessageMapping("/profile/update")
    @SendTo("/topic/profile")
    public User updateProfile(User user) {
        // Doar retransmite profilul actualizat către toți clienții
        return user;
    }
}
