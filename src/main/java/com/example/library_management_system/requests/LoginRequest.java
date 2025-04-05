package com.example.library_management_system.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
