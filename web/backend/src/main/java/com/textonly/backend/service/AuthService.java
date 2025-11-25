package com.textonly.backend.service;

import com.textonly.backend.model.User;
import com.textonly.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Înregistrare utilizator nou
    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email-ul este deja folosit!");
        }
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Numele de utilizator este deja folosit!");
        }

        User newUser = new User(username, email, passwordEncoder.encode(password));
        return userRepository.save(newUser);
    }

    // ✅ Autentificare utilizator
    public User loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Utilizatorul nu există!");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Parola este greșită!");
        }

        return user;
    }
}
