package com.textonly.backend.controller;

import com.textonly.backend.model.Gift;
import com.textonly.backend.model.InventoryItem;
import com.textonly.backend.model.StoreItem;
import com.textonly.backend.model.TransactionLog;
import com.textonly.backend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/store")
@CrossOrigin(origins = "*")
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * Get all store items
     */
    @GetMapping("/items")
    public ResponseEntity<List<StoreItem>> getAllItems() {
        return ResponseEntity.ok(storeService.getAllStoreItems());
    }

    /**
     * Get store items by type
     */
    @GetMapping("/items/{type}")
    public ResponseEntity<List<StoreItem>> getItemsByType(@PathVariable String type) {
        return ResponseEntity.ok(storeService.getStoreItemsByType(type));
    }

    /**
     * Buy an item
     * Request body: { "userId": 1, "itemId": 5 }
     */
    @PostMapping("/buy")
    public ResponseEntity<Map<String, Object>> buyItem(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long itemId = request.get("itemId");
        return ResponseEntity.ok(storeService.buyItem(userId, itemId));
    }

    /**
     * Get user's inventory
     */
    @GetMapping("/inventory/{userId}")
    public ResponseEntity<List<InventoryItem>> getUserInventory(@PathVariable Long userId) {
        return ResponseEntity.ok(storeService.getUserInventory(userId));
    }

    /**
     * Get user's transaction history
     */
    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<TransactionLog>> getUserTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok(storeService.getUserTransactions(userId));
    }

    /**
     * Add coins to user (for purchases)
     * Request body: { "userId": 1, "amount": 100, "description": "Purchased 100 coins" }
     */
    @PostMapping("/coins/add")
    public ResponseEntity<Map<String, Object>> addCoins(@RequestBody Map<String, Object> request) {
        Long userId = Long.parseLong(request.get("userId").toString());
        Integer amount = Integer.parseInt(request.get("amount").toString());
        String description = request.get("description").toString();
        return ResponseEntity.ok(storeService.addCoins(userId, amount, description));
    }

    /**
     * Remove coins from user (for selling)
     * Request body: { "userId": 1, "amount": 50, "description": "Sold 50 coins" }
     */
    @PostMapping("/coins/remove")
    public ResponseEntity<Map<String, Object>> removeCoins(@RequestBody Map<String, Object> request) {
        Long userId = Long.parseLong(request.get("userId").toString());
        Integer amount = Integer.parseInt(request.get("amount").toString());
        String description = request.get("description").toString();
        return ResponseEntity.ok(storeService.removeCoins(userId, amount, description));
    }

    /**
     * Send gift to another user
     * Request body: { "senderId": 1, "receiverId": 2, "giftName": "Trandafir", "giftValue": 10, "giftResource": "ic_rose" }
     */
    @PostMapping("/gift/send")
    public ResponseEntity<Map<String, Object>> sendGift(@RequestBody Map<String, Object> request) {
        Long senderId = Long.parseLong(request.get("senderId").toString());
        Long receiverId = Long.parseLong(request.get("receiverId").toString());
        String giftName = request.get("giftName").toString();
        Integer giftValue = Integer.parseInt(request.get("giftValue").toString());
        String giftResource = request.get("giftResource").toString();
        return ResponseEntity.ok(storeService.sendGift(senderId, receiverId, giftName, giftValue, giftResource));
    }

    /**
     * Get available gifts
     */
    @GetMapping("/gifts")
    public ResponseEntity<List<Gift>> getAvailableGifts() {
        return ResponseEntity.ok(storeService.getAvailableGifts());
    }
}
