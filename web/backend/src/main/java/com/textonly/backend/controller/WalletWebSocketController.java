package com.textonly.backend.controller;

import com.textonly.backend.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WalletWebSocketController {
    @MessageMapping("/wallet/update")
    @SendTo("/topic/wallet")
    public User updateWallet(User user) {
        // Retransmite soldul actualizat către toți clienții
        return user;
    }
}
