package com.nosliejnav.libraryapi.service.impl;

import com.nosliejnav.libraryapi.model.entity.Loan;
import com.nosliejnav.libraryapi.model.repository.LoanRepository;
import com.nosliejnav.libraryapi.service.LoanService;

public class LoanServiceImpl implements LoanService {

    private LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {

        this.repository = repository;

    }

    @Override
    public Loan save(Loan loan) {
        return repository.save(loan);
    }

}
