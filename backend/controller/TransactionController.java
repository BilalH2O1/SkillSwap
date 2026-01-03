package com.skillswap.backend.controller;

import com.skillswap.backend.model.Transaction;
import com.skillswap.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/pay")
    public Transaction transfer(@RequestParam Long senderId,
                                @RequestParam Long receiverId,
                                @RequestParam Double amount) {
        return transactionService.transferHours(senderId, receiverId, amount);
    }

    @GetMapping("/history/{userId}")
    public List<Transaction> getHistory(@PathVariable Long userId) {
        return transactionService.getHistory(userId);
    }

}
