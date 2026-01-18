# TextOnly Backend - API Documentation

## Actualizări și Endpoint-uri Noi

Acest document descrie toate endpoint-urile integrate din aplicația Android în backend-ul Spring Boot.

## 📍 Endpoint-uri

### 🔐 Autentificare (Auth)

#### POST `/auth/qr/validate`
Validează un token QR cu numărul de telefon
```json
Request:
{
  "token": "abc123",
  "phoneNumber": "+40123456789"
}

Response: boolean (true/false)
```

### 👤 Profil (Profile)

#### GET `/users/profile/{userId}`
Obține profilul utilizatorului
```json
Response:
{
  "id": 1,
  "username": "john",
  "email": "john@example.com",
  "displayName": "John Doe",
  "profileImageUri": "...",
  "coinBalance": 100,
  "walletBalance": 50.0
}
```

#### POST `/users/profile`
Actualizează profilul utilizatorului
```json
Request:
{
  "userId": 1,
  "displayName": "John Doe",
  "profileImageUri": "https://..."
}

Response:
{
  "success": true,
  "message": "Profile updated successfully",
  "user": { ... }
}
```

#### GET `/users/balance/{userId}`
Obține balanța utilizatorului
```json
Response:
{
  "coinBalance": 100,
  "walletBalance": 50.0
}
```

### 🏪 Magazin (Store)

#### GET `/store/items`
Obține toate itemurile din magazin
```json
Response: [
  {
    "id": 1,
    "name": "Frame Ploaie",
    "type": "FRAME",
    "price": 50,
    "resourceName": "ic_frame_rain"
  },
  ...
]
```

#### GET `/store/items/{type}`
Obține itemuri după tip (FRAME, EMOTICON)

#### POST `/store/buy`
Cumpără un item
```json
Request:
{
  "userId": 1,
  "itemId": 5
}

Response:
{
  "success": true,
  "message": "Articol cumpărat cu succes",
  "newBalance": 50
}
```

#### GET `/store/inventory/{userId}`
Obține inventarul utilizatorului
```json
Response: [
  {
    "id": 1,
    "itemName": "Frame Ploaie",
    "itemType": "FRAME",
    "resourceName": "ic_frame_rain",
    "acquiredDate": 1234567890
  },
  ...
]
```

#### GET `/store/transactions/{userId}`
Obține istoricul tranzacțiilor
```json
Response: [
  {
    "id": 1,
    "description": "Cumpărat: Frame Ploaie",
    "amount": -50,
    "type": "PURCHASE",
    "timestamp": 1234567890
  },
  ...
]
```

### 💰 OnlyCoins

#### POST `/store/coins/add`
Adaugă coins (pentru achiziții)
```json
Request:
{
  "userId": 1,
  "amount": 100,
  "description": "Purchased 100 coins"
}

Response:
{
  "success": true,
  "newBalance": 200
}
```

#### POST `/store/coins/remove`
Scoate coins (pentru vânzări)
```json
Request:
{
  "userId": 1,
  "amount": 50,
  "description": "Sold 50 coins"
}

Response:
{
  "success": true,
  "newBalance": 150
}
```

### 🎁 Cadouri (Gifts)

#### GET `/store/gifts`
Obține lista de cadouri disponibile
```json
Response: [
  {
    "name": "Trandafir",
    "iconResourceName": "ic_rose",
    "price": 10
  },
  {
    "name": "Inimă",
    "iconResourceName": "ic_heart",
    "price": 20
  },
  ...
]
```

#### POST `/store/gift/send`
Trimite un cadou
```json
Request:
{
  "senderId": 1,
  "receiverId": 2,
  "giftName": "Trandafir",
  "giftValue": 10,
  "giftResource": "ic_rose"
}

Response:
{
  "success": true,
  "message": "Cadou trimis cu succes",
  "senderBalance": 90,
  "receiverBalance": 110
}
```

### 🤖 AI Chat

#### POST `/ai/chat`
Chat cu AI (ChatGPT)
```json
Request:
{
  "message": "Hello AI"
}

Response:
{
  "success": true,
  "response": "AI Response: ..."
}
```

**Note:** Momentan returnează un răspuns simplu. Pentru integrare completă cu ChatGPT, actualizați `AiController.java`.

### 📱 App Update

#### GET `/app/version`
Obține informații despre versiune
```json
Response:
{
  "versionCode": 1,
  "versionName": "1.0.0",
  "url": "https://example.com/textonly-latest.apk",
  "updateAvailable": false
}
```

#### GET `/app/version/check?currentVersion={versionCode}`
Verifică dacă e disponibil update
```json
Response:
{
  "updateAvailable": true,
  "latestVersion": 2,
  "versionName": "1.0.1",
  "url": "https://example.com/textonly-latest.apk",
  "message": "O nouă versiune este disponibilă!"
}
```

## 📡 WebSocket Endpoints

### Mesaje
- **Send message:** `/app/send` → broadcast la `/topic/messages`
- **Send gift:** `/app/gift/send` → broadcast la `/topic/gifts`
- **Send invite:** `/app/invite/send` → broadcast la `/topic/invites`

## 🗄️ Entități și Modele Noi

### StoreItem
- `id`: Long
- `name`: String
- `type`: String (FRAME, EMOTICON)
- `price`: Integer
- `resourceName`: String

### InventoryItem
- `id`: Long
- `user`: User
- `itemName`: String
- `itemType`: String
- `resourceName`: String
- `acquiredDate`: Long

### TransactionLog
- `id`: Long
- `user`: User
- `description`: String
- `amount`: Integer (negativ pentru cheltuieli)
- `type`: String (PURCHASE, GIFT_SENT, GIFT_RECEIVED, BUY, SELL)
- `timestamp`: Long

### User (Actualizat)
- Added: `coinBalance`: Integer

### Message (Actualizat)
- Added: `type`: String (TEXT, FILE, INVITE, GIFT)
- Added: File message fields
- Added: Invite message fields
- Added: Gift message fields

## 🚀 Cum să Rulezi

1. Asigură-te că PostgreSQL rulează
2. Actualizează `application.properties` cu credențialele tale
3. Rulează: `mvn spring-boot:run`
4. Serverul va rula pe `http://localhost:8080`

## 📝 Note

- Toate endpoint-urile au `@CrossOrigin(origins = "*")` pentru dezvoltare
- Pentru producție, configurează CORS corespunzător
- AI Chat endpoint necesită integrare cu ChatGPT API
- Update checker trebuie configurat cu URL-ul corect de download

## 🔄 Sincronizare cu Android

Toate endpoint-urile sunt configurate să corespundă cu `Config.kt` din aplicația Android:
- `BASE_URL`: https://your-domain.com
- `QR_VALIDATE_URL`: /auth/qr/validate
- `PROFILE_UPDATE_URL`: /users/profile
- `CHAT_GPT_URL`: /ai/chat
- `STORE_ITEMS_URL`: /store/items
- `BUY_ITEM_URL`: /store/buy
- `INVENTORY_URL`: /store/inventory
- `APP_UPDATE_URL`: /app/version
