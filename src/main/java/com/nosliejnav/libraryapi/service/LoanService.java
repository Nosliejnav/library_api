package com.nosliejnav.libraryapi.service;

import java.util.Optional;

import com.nosliejnav.libraryapi.api.dto.LoanFilterDTO;
import com.nosliejnav.libraryapi.model.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);

    Page<Loan> find(LoanFilterDTO filterDTO, Pageable pageable);
}
