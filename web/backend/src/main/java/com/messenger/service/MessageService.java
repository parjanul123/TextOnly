package com.messenger.service;

import com.messenger.model.Message;
import com.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getMessagesForUser(String phoneNumber) {
        return messageRepository.findByFromOrTo(phoneNumber, phoneNumber);
    }
}
