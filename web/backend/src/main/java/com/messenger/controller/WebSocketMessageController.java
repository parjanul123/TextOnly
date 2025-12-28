package com.messenger.controller;

import com.messenger.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketMessageController {
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message send(Message message) {
        return message;
    }
}
