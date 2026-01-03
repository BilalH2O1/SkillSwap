package com.skillswap.backend.repository;

import com.skillswap.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // This is the line your code was looking for!
    List<Transaction> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}