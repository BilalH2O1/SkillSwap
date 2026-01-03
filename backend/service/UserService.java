package com.skillswap.backend.service;

import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User registerUser(User newUser){
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new RuntimeException("Email already taken!");

        }
        return userRepository.save(newUser);
    }

    public User getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User addMoney (Long userId, Double amount){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found."));

        user.setBalance(user.getBalance() + amount);

        return userRepository.save(user);
    }
}
