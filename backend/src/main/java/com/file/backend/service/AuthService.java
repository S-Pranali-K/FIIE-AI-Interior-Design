package com.file.backend.service;

import com.file.backend.entity.User;
import com.file.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String name, String email, String password) {

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            return "Email already registered";
        }

        User user = new User(name, email, password);
        userRepository.save(user);

        return "Registration successful";
    }

    public User login(String email, String password) {

        System.out.println("LOGIN EMAIL RECEIVED = [" + email + "]");

        Optional<User> userOptional = userRepository.findByEmail(email);

        System.out.println("USER FOUND = " + userOptional.isPresent());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}