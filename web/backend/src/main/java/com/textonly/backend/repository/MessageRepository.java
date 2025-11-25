package com.textonly.backend.repository;

import com.textonly.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // ✅ Obține toate mesajele dintre doi utilizatori (chat privat)
    List<Message> findBySenderAndReceiverOrReceiverAndSender(
            String sender1, String receiver1,
            String sender2, String receiver2
    );
}
