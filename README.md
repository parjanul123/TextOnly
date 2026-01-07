# TextOnly - Aplicație de Mesagerie (Tip WhatsApp)

## Structură proiect

```
TextOnly/
├── backend/      # Server Spring Boot (API, WebSocket, QR, autentificare)
├── mobile/       # Aplicație Android (creare cont, scanare QR, chat)
├── web/          # Frontend web (login QR, chat web)
├── turn-server/  # Server TURN pentru WebRTC (apeluri video/audio)
├── ngrok.exe     # Utilitar pentru expunere rapidă backend
```

## Flux de autentificare și sesiune

1. **Creare cont (doar pe mobil):**
   - Utilizatorul introduce numărul de telefon în aplicația Android.
   - Primește SMS cu cod de verificare.
   - După validare, contul este creat și token-ul de autentificare este stocat pe mobil.

2. **Login pe web (fără parolă):**
   - Utilizatorul accesează web-ul și apasă "Conectează-te cu telefonul".
   - Web-ul cere backend-ului generarea unui cod QR unic (sesiune temporară).
   - QR-ul conține un token/sessionId generat de backend.
   - Utilizatorul scanează QR-ul cu aplicația mobilă (funcție de scanare în app).
   - Aplicația mobilă trimite către backend: sessionId + token-ul de autentificare al userului.
   - Backend-ul validează și marchează sesiunea QR ca "autentificată".
   - Web-ul verifică periodic (polling sau WebSocket) dacă sesiunea QR a fost validată.
   - După validare, web-ul primește token-ul de autentificare și accesează chat-ul.

## Endpoints backend (exemple)

- `POST /api/auth/request-qr-session` → Generează sesiune QR și returnează sessionId + QR data
- `POST /api/auth/validate-qr-session` → (mobil) Trimite sessionId + token user, marchează sesiunea ca validată
- `GET /api/auth/check-qr-session?sessionId=...` → (web) Verifică dacă sesiunea a fost validată

## Securitate
- Token-ul de autentificare nu se transmite niciodată direct în browser, doar prin backend.
- Sesiunile QR expiră rapid (ex: 1-2 minute).
- Toate comunicațiile se fac pe HTTPS (folosește ngrok pentru testare externă).

## Recomandări
- Ține backend-ul pornit cu `mvn spring-boot:run` din folderul web/backend.
- Rulează `ngrok.exe http 8080` din folderul TextOnly pentru expunere rapidă.
- Folosește aplicația mobilă pentru login și scanare QR.
- Poți adăuga și un client web pentru chat, folosind WebSocket pentru mesaje în timp real.

---

**Acest README descrie fluxul de login și integrarea între mobile și web pentru o aplicație de mesagerie modernă, similară cu WhatsApp.**
