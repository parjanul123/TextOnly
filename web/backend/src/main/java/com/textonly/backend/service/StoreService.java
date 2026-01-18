package com.textonly.backend.service;

import com.textonly.backend.model.*;
import com.textonly.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreService {

    @Autowired
    private StoreItemRepository storeItemRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all store items
     */
    public List<StoreItem> getAllStoreItems() {
        return storeItemRepository.findAll();
    }

    /**
     * Get store items by type
     */
    public List<StoreItem> getStoreItemsByType(String type) {
        return storeItemRepository.findByType(type);
    }

    /**
     * Buy a store item
     */
    @Transactional
    public Map<String, Object> buyItem(Long userId, Long itemId) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StoreItem item = storeItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Check if user has enough coins
        if (user.getCoinBalance() < item.getPrice()) {
            response.put("success", false);
            response.put("message", "Fonduri insuficiente");
            return response;
        }

        // Deduct coins
        user.setCoinBalance(user.getCoinBalance() - item.getPrice());
        userRepository.save(user);

        // Add to inventory
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setUser(user);
        inventoryItem.setItemName(item.getName());
        inventoryItem.setItemType(item.getType());
        inventoryItem.setResourceName(item.getResourceName());
        inventoryItem.setAcquiredDate(System.currentTimeMillis());
        inventoryItemRepository.save(inventoryItem);

        // Log transaction
        TransactionLog log = new TransactionLog();
        log.setUser(user);
        log.setDescription("Cumpărat: " + item.getName());
        log.setAmount(-item.getPrice());
        log.setType("PURCHASE");
        log.setTimestamp(System.currentTimeMillis());
        transactionLogRepository.save(log);

        response.put("success", true);
        response.put("message", "Articol cumpărat cu succes");
        response.put("newBalance", user.getCoinBalance());
        return response;
    }

    /**
     * Get user's inventory
     */
    public List<InventoryItem> getUserInventory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return inventoryItemRepository.findByUser(user);
    }

    /**
     * Get user's transaction history
     */
    public List<TransactionLog> getUserTransactions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionLogRepository.findByUserOrderByTimestampDesc(user);
    }

    /**
     * Add coins to user (for purchases)
     */
    @Transactional
    public Map<String, Object> addCoins(Long userId, Integer amount, String description) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setCoinBalance(user.getCoinBalance() + amount);
        userRepository.save(user);

        // Log transaction
        TransactionLog log = new TransactionLog();
        log.setUser(user);
        log.setDescription(description);
        log.setAmount(amount);
        log.setType("BUY");
        log.setTimestamp(System.currentTimeMillis());
        transactionLogRepository.save(log);

        response.put("success", true);
        response.put("newBalance", user.getCoinBalance());
        return response;
    }

    /**
     * Remove coins from user (for selling)
     */
    @Transactional
    public Map<String, Object> removeCoins(Long userId, Integer amount, String description) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getCoinBalance() < amount) {
            response.put("success", false);
            response.put("message", "Fonduri insuficiente");
            return response;
        }

        user.setCoinBalance(user.getCoinBalance() - amount);
        userRepository.save(user);

        // Log transaction
        TransactionLog log = new TransactionLog();
        log.setUser(user);
        log.setDescription(description);
        log.setAmount(-amount);
        log.setType("SELL");
        log.setTimestamp(System.currentTimeMillis());
        transactionLogRepository.save(log);

        response.put("success", true);
        response.put("newBalance", user.getCoinBalance());
        return response;
    }

    /**
     * Send gift to another user
     */
    @Transactional
    public Map<String, Object> sendGift(Long senderId, Long receiverId, String giftName, Integer giftValue, String giftResource) {
        Map<String, Object> response = new HashMap<>();

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Check if sender has enough coins
        if (sender.getCoinBalance() < giftValue) {
            response.put("success", false);
            response.put("message", "Fonduri insuficiente");
            return response;
        }

        // Deduct from sender
        sender.setCoinBalance(sender.getCoinBalance() - giftValue);
        userRepository.save(sender);

        // Add to receiver
        receiver.setCoinBalance(receiver.getCoinBalance() + giftValue);
        userRepository.save(receiver);

        // Log transactions
        TransactionLog senderLog = new TransactionLog();
        senderLog.setUser(sender);
        senderLog.setDescription("Cadou trimis: " + giftName);
        senderLog.setAmount(-giftValue);
        senderLog.setType("GIFT_SENT");
        senderLog.setTimestamp(System.currentTimeMillis());
        transactionLogRepository.save(senderLog);

        TransactionLog receiverLog = new TransactionLog();
        receiverLog.setUser(receiver);
        receiverLog.setDescription("Cadou primit: " + giftName);
        receiverLog.setAmount(giftValue);
        receiverLog.setType("GIFT_RECEIVED");
        receiverLog.setTimestamp(System.currentTimeMillis());
        transactionLogRepository.save(receiverLog);

        response.put("success", true);
        response.put("message", "Cadou trimis cu succes");
        response.put("senderBalance", sender.getCoinBalance());
        response.put("receiverBalance", receiver.getCoinBalance());
        return response;
    }

    /**
     * Get available gifts
     */
    public List<Gift> getAvailableGifts() {
        return List.of(
                new Gift("Trandafir", "ic_rose", 10),
                new Gift("Inimă", "ic_heart", 20),
                new Gift("Rachetă", "ic_rocket", 50),
                new Gift("Card Cadou", "ic_gift_card", 100)
        );
    }
}
