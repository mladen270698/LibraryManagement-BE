package com.example.library_management_system.controllers;

import com.example.library_management_system.models.Book;
import com.example.library_management_system.requests.BookSearchPayload;
import com.example.library_management_system.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/insert")
    public Book insert(@RequestBody Book book) {
        return bookService.insert(book);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Book>> search(@RequestBody BookSearchPayload bookSearchPayload) {
        try {
            List<Book> books = bookService.search(bookSearchPayload);
            return ResponseEntity.ok(books);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{id_book}")
    public Optional<Book> byId(@PathVariable Integer id_book) {
        return bookService.byId(id_book);
    }

    @PutMapping("/{id_book}")
    public ResponseEntity<Book> update(@PathVariable Integer id_book, @RequestBody Book bookToUpdate) {
        try {
            Book updatedBook = bookService.update(id_book, bookToUpdate);
            return ResponseEntity.ok(bookToUpdate);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_book}")
    public ResponseEntity<Void> delete(@PathVariable Integer id_book) {
        bookService.delete(id_book);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
