package com.messenger.controller;

import com.messenger.model.QrSession;
import com.messenger.service.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
            // POST /api/auth/qr/logout - invalidează ultima sesiune QR validată
            @PostMapping("/qr/logout")
            public void logoutQrSession() {
                qrService.logoutLastValidatedSession();
            }
    // GET /api/auth/qr/session?token=... - returnează numărul de telefon pentru tokenul QR dat
    @GetMapping("/qr/session")
    public Map<String, String> getQrSessionPhone(@RequestParam String token) {
        QrSession session = qrService.getSessionByToken(token);
        String phone = (session != null && session.isValidated()) ? session.getPhoneNumber() : null;
        return Map.of("phoneNumber", phone);
    }
    @Autowired
    private QrService qrService;

    // POST /api/auth/qr - generează token QR
    @PostMapping("/qr")
    public QrSession generateQrToken() {
        return qrService.generateToken();
    }

    // POST /api/auth/qr/validate - validează token QR cu telefonul
    @PostMapping("/qr/validate")
    public boolean validateQrToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String phoneNumber = body.get("phoneNumber");
        return qrService.validateToken(token, phoneNumber);
    }

    // GET /api/auth/qr/status/{token} - verifică dacă token-ul a fost validat
    @GetMapping("/qr/status/{token}")
    public boolean isQrTokenValidated(@PathVariable String token) {
        return qrService.isTokenValidated(token);
    }
}
