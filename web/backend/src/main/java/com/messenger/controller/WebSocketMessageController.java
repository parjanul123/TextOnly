package com.messenger.controller;

import com.messenger.model.Message;
import com.textonly.backend.model.GiftMessage;
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

    @MessageMapping("/gift/send")
    @SendTo("/topic/gifts")
    public GiftMessage sendGift(GiftMessage giftMessage) {
        // Gift message will be broadcast to all subscribers
        return giftMessage;
    }

    @MessageMapping("/invite/send")
    @SendTo("/topic/invites")
    public Message sendInvite(Message invite) {
        // Server invite will be broadcast
        return invite;
    }
}
