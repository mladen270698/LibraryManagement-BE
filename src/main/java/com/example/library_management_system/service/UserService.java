package com.example.library_management_system.service;

import com.example.library_management_system.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.library_management_system.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User insert(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public Optional<User> byId(Integer id_admin) {
        return userRepository.findById(id_admin);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public List<User> search() {
        return userRepository.findAll();
    }

    public void delete(Integer id_admin) {
        userRepository.deleteById(id_admin);
    }
}
