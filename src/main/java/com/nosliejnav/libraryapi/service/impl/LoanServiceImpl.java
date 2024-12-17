package com.nosliejnav.libraryapi.service.impl;

import java.util.Optional;

import com.nosliejnav.libraryapi.api.exception.BusinessException;
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
        if (repository.existsByBookAndNotReturned(loan.getBook())) {
            throw new BusinessException("Book already loaned");
        }
        return repository.save(loan);
    }

    @Override
    public Optional<Loan> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Loan update(Loan loan) {
        return null;
    }

}
