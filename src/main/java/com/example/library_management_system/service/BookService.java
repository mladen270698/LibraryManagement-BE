package com.example.library_management_system.service;

import com.example.library_management_system.models.Book;
import com.example.library_management_system.repositories.BookRepository;
import com.example.library_management_system.requests.BookSearchPayload;
import com.example.library_management_system.utils.SpecificationUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book insert(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> byId(Integer id_book) {
        return bookRepository.findById(id_book);
    }

    public Book update(Integer id_book, Book bookToUpdate) {
        Book existingBook = bookRepository.findById(id_book)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID: " + id_book + " not found"));

        existingBook.setTitle(bookToUpdate.getTitle());
        existingBook.setAuthor(bookToUpdate.getAuthor());
        existingBook.setRelease_date(bookToUpdate.getRelease_date());
        existingBook.setQuantity(bookToUpdate.getQuantity());

        return bookRepository.save(existingBook);
    }

    public List<Book> search(BookSearchPayload bookSearchPayload) {
        Specification<Book> specification = SpecificationUtils.buildSpecification(
                SpecificationUtils.likeFilter("title", bookSearchPayload.getTitle()),
                SpecificationUtils.likeFilter("author", bookSearchPayload.getAuthor())
        );
        return bookRepository.findAll(specification);
    }

    public void delete(Integer id_book) {
        bookRepository.deleteById(id_book);
    }
}
