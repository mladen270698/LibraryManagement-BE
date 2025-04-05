package com.example.library_management_system.repositories;

import com.example.library_management_system.models.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Integer>, JpaSpecificationExecutor<BorrowedBook> {
}
