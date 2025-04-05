package com.example.library_management_system.controllers;

import com.example.library_management_system.models.BorrowedBook;
import com.example.library_management_system.requests.BorrowedBookSearchPayload;
import com.example.library_management_system.service.BorrowedBookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/BorrowedBook")
public class BorrowedBookController {

    @Autowired
    BorrowedBookService borrowedBookService;


    @PostMapping("/Insert")
    public BorrowedBook insert(@RequestBody BorrowedBook borrowedBook) {
        return borrowedBookService.insert(borrowedBook);
    }

    @PostMapping("/Search")
    public ResponseEntity<List<BorrowedBook>> search(@RequestBody BorrowedBookSearchPayload borrowedBookSearchPayload) {
        try {
            List<BorrowedBook> borrowedBooks = borrowedBookService.search(borrowedBookSearchPayload);
            return ResponseEntity.ok(borrowedBooks);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{id_book}")
    public Optional<BorrowedBook> byId(@PathVariable Integer id_book) {
        return borrowedBookService.byId(id_book);
    }

    @DeleteMapping("/{id_book}")
    public ResponseEntity<Void> delete(@PathVariable Integer id_book) {
        borrowedBookService.delete(id_book);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
