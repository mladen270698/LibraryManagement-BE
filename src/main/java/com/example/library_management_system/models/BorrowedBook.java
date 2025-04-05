package com.example.library_management_system.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Integer id;
    private String firstname;
    private String lastname;
    private String date_take;
    private String date_return;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book", referencedColumnName = "id_book")  // Foreign key to the Book table
    private Book book;
}
