package com.nosliejnav.libraryapi.api.resource;

import com.nosliejnav.libraryapi.model.entity.Loan.LoanBuilder;
import com.nosliejnav.libraryapi.api.dto.BookDTO;
import com.nosliejnav.libraryapi.api.dto.LoanDTO;
import com.nosliejnav.libraryapi.api.dto.LoanFilterDTO;
import com.nosliejnav.libraryapi.api.dto.ReturnedLoanDTO;
import com.nosliejnav.libraryapi.model.entity.Book;
import com.nosliejnav.libraryapi.model.entity.Loan;
import com.nosliejnav.libraryapi.service.BookService;
import com.nosliejnav.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    private final BookService bookService;

    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO loanDTO) {
        Book book = bookService
                .getBookByIsbn(loanDTO.getIsbn())
                .orElseThrow( () ->
                        new ResponseStatusException( HttpStatus.BAD_REQUEST, "Book not found for passed isbn"));
        Loan entity = Loan.builder()
                .book(book)
                .customer(loanDTO.getCustomer())
                .loanDate(LocalDate.now())
                .build();

        entity = loanService.save(entity);
        return entity.getId();
    }

    @PatchMapping("{id}")
    public void returnBook(
            @PathVariable Long id,
            @RequestBody ReturnedLoanDTO returnedLoanDTO) {
        Loan loan = loanService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        loan.setReturned(returnedLoanDTO.getReturned());
        loanService.update(loan);
    }

    @GetMapping
    public Page<LoanDTO> find(LoanFilterDTO loanFilterDTO, Pageable pageRequest){
        Page<Loan> result = loanService.find(loanFilterDTO, pageRequest);
        List<LoanDTO> loans = result
                .getContent()
                .stream()
                .map(entity -> {

                    Book book = entity.getBook();
                    BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
                    LoanDTO loanDTO = modelMapper.map(entity, LoanDTO.class);
                    loanDTO.setBook(bookDTO);
                    return loanDTO;

                }).collect(Collectors.toList());
        return new PageImpl<LoanDTO>(loans, pageRequest, result.getTotalElements());
    }
}
