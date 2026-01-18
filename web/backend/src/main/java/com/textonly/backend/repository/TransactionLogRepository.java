package com.textonly.backend.repository;

import com.textonly.backend.model.TransactionLog;
import com.textonly.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    List<TransactionLog> findByUserOrderByTimestampDesc(User user);
    List<TransactionLog> findByUserAndType(User user, String type);
}
