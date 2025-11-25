package com.textonly.backend.service;

import com.textonly.backend.model.Message;
import com.textonly.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final MessageRepository messageRepository;

    @Autowired
    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // ✅ Trimite (salvează) un mesaj nou
    public Message sendMessage(String sender, String receiver, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    // ✅ Obține toate mesajele dintre doi utilizatori
    public List<Message> getChatBetween(String user1, String user2) {
        return messageRepository.findBySenderAndReceiverOrReceiverAndSender(
                user1, user2,
                user1, user2
        );
    }
}
