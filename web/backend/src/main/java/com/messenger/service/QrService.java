package com.messenger.service;

import com.messenger.model.QrSession;
import com.messenger.repository.QrSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class QrService {
                // Returnează sesiunea QR după token
                public QrSession getSessionByToken(String token) {
                    return qrSessionRepository.findById(token).orElse(null);
                }
            // Invalidează ultima sesiune QR validată (mock/demo)
            public void logoutLastValidatedSession() {
                QrSession session = getLastValidatedSession();
                if (session != null) {
                    session.setValidated(false);
                    qrSessionRepository.save(session);
                }
            }
        // Returnează ultima sesiune QR validată (mock/demo)
        public QrSession getLastValidatedSession() {
            return qrSessionRepository.findAll().stream()
                .filter(QrSession::isValidated)
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .findFirst()
                .orElse(null);
        }
    @Autowired
    private QrSessionRepository qrSessionRepository;

    public QrSession generateToken() {
        QrSession session = new QrSession();
        session.setToken(UUID.randomUUID().toString());
        session.setCreatedAt(LocalDateTime.now());
        session.setValidated(false);
        qrSessionRepository.save(session);
        return session;
    }

    public boolean validateToken(String token, String phoneNumber) {
        Optional<QrSession> sessionOpt = qrSessionRepository.findById(token);
        if (sessionOpt.isPresent()) {
            QrSession session = sessionOpt.get();
            if (!session.isValidated() && session.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(5))) {
                session.setPhoneNumber(phoneNumber);
                session.setValidated(true);
                qrSessionRepository.save(session);
                return true;
            }
        }
        return false;
    }

    public boolean isTokenValidated(String token) {
        Optional<QrSession> sessionOpt = qrSessionRepository.findById(token);
        return sessionOpt.map(QrSession::isValidated).orElse(false);
    }
}
