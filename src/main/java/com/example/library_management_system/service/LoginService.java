package com.example.library_management_system.service;

import com.example.library_management_system.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.library_management_system.repositories.UserRepository;
import com.example.library_management_system.requests.LoginRequest;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if(user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials");
    }
}
