package com.example.library_management_system.controllers;

import com.example.library_management_system.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.library_management_system.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/insert")
    public User insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @PostMapping("/search")
    public List<User> search() {
        return userService.search();
    }

    @GetMapping("/{id_user}")
    public Optional<User> byId(@PathVariable Integer id_user) {
        return userService.byId(id_user);
    }

    @PutMapping("/{id_user}")
    public ResponseEntity<User> update(
            @PathVariable Integer id_user,
            @RequestBody User userToUpdate
    ) {
        Optional<User> existingUser = userService.byId(id_user);

        if(existingUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = existingUser.get();
        user.setFirstname(userToUpdate.getFirstname());
        user.setLastname(userToUpdate.getLastname());
        user.setPassword(
                passwordEncoder.encode(userToUpdate.getPassword())
        );
        user.setUsername(userToUpdate.getUsername());

        User savedUser = userService.update(user);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id_user}")
    public ResponseEntity<Void> delete(@PathVariable Integer id_user) {
        userService.delete(id_user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful");
    }
}
