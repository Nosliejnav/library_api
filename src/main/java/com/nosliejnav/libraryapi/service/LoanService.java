package com.nosliejnav.libraryapi.service;

import java.util.Optional;

import com.nosliejnav.libraryapi.model.entity.Loan;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);
}
