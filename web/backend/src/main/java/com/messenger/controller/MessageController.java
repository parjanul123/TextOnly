package com.messenger.controller;

import com.messenger.model.Message;
import com.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/{phoneNumber}")
    public List<Message> getMessages(@PathVariable String phoneNumber) {
        return messageService.getMessagesForUser(phoneNumber);
    }
}
