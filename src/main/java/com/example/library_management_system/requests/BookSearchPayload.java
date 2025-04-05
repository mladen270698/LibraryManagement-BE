package com.example.library_management_system.requests;

import lombok.*;

@Data
public class BookSearchPayload {
    private String title;
    private String author;
}
