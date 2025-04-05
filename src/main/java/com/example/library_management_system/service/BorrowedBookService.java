package com.example.library_management_system.service;

import com.example.library_management_system.models.Book;
import com.example.library_management_system.models.BorrowedBook;
import com.example.library_management_system.repositories.BookRepository;
import com.example.library_management_system.repositories.BorrowedBookRepository;
import com.example.library_management_system.requests.BorrowedBookSearchPayload;
import com.example.library_management_system.utils.SpecificationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBookService {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public BorrowedBook insert(BorrowedBook borrowedBook) {
        Book book = borrowedBook.getBook(); // Assumes book is already attached

        if (book == null || book.getId_book() == null) {
            throw new IllegalArgumentException("Book is required to borrow.");
        }

        Book dbBook = bookRepository.findById(book.getId_book())
                .orElseThrow(() -> new RuntimeException("Book not found."));

        if (dbBook.getQuantity() <= 0) {
            throw new RuntimeException("Book is currently not available.");
        }

        dbBook.setQuantity(dbBook.getQuantity() - 1);
        bookRepository.save(dbBook);

        borrowedBook.setBook(dbBook); // Just to be sure you're saving managed instance
        return borrowedBookRepository.save(borrowedBook);
    }

    public Optional<BorrowedBook> byId(Integer id) {
        return borrowedBookRepository.findById(id);
    }

    public List<BorrowedBook> search(BorrowedBookSearchPayload payload) {
        Specification<BorrowedBook> specification = SpecificationUtils.buildSpecification(
                SpecificationUtils.likeFilter("firstname", payload.getFirstname()),
                SpecificationUtils.likeFilter("lastname", payload.getLastname())
        );

        return borrowedBookRepository.findAll(specification);
    }

    @Transactional
    public void delete(Integer id) {
        BorrowedBook borrowed = borrowedBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrowed book record not found."));

        Book book = borrowed.getBook();
        if (book != null) {
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }

        borrowedBookRepository.deleteById(id);
    }
}
