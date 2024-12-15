package com.nosliejnav.libraryapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nosliejnav.libraryapi.model.entity.Book;
import com.nosliejnav.libraryapi.model.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByBookAndNotReturned(Book book);

}
