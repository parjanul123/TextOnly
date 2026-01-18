package com.textonly.backend.repository;

import com.textonly.backend.model.InventoryItem;
import com.textonly.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByUser(User user);
    List<InventoryItem> findByUserAndItemType(User user, String itemType);
}
