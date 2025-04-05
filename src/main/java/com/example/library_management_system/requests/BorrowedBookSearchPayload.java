package com.example.library_management_system.requests;

import lombok.Data;

@Data
public class BorrowedBookSearchPayload {
    private String firstname;
    private String lastname;
}
