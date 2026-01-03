package com.skillswap.backend.service;

import com.skillswap.backend.model.Transaction;
import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.TransactionRepository;
import com.skillswap.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Transaction transferHours(Long senderId, Long receiverId, Double amount) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getId() == receiver.getId()) {
            throw new RuntimeException("You cannot pay yourself!");
        }
        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance!");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getHistory(Long userId) {
        return transactionRepository.findBySenderIdOrReceiverId(userId, userId);
    }
}