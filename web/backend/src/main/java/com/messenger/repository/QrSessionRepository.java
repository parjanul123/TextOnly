package com.messenger.repository;

import com.messenger.model.QrSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrSessionRepository extends JpaRepository<QrSession, String> {
}
