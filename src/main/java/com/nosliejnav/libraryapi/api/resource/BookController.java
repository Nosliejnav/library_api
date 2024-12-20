package com.nosliejnav.libraryapi.api.resource;

import com.nosliejnav.libraryapi.api.dto.BookDTO;
import com.nosliejnav.libraryapi.api.dto.LoanDTO;
import com.nosliejnav.libraryapi.model.entity.Book;
import com.nosliejnav.libraryapi.model.entity.Loan;
import com.nosliejnav.libraryapi.service.BookService;
import com.nosliejnav.libraryapi.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final ModelMapper modelMapper;

    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        Book entity = modelMapper.map(bookDTO, Book.class);
        entity = bookService.save(entity);
        return modelMapper.map(entity, BookDTO.class);
    }

    @GetMapping("{id}")
    public BookDTO get(@PathVariable Long id) {
        return bookService
                .getById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Book book = bookService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bookService.delete(book);
    }

    @PutMapping("{id}")
    public BookDTO update(@PathVariable Long id, BookDTO dto) {

        return bookService.getById(id).map(book -> {

            book.setAuthor(dto.getAuthor());
            book.setTitle(dto.getTitle());
            book = bookService.update(book);
            return modelMapper.map(book, BookDTO.class);

        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Page<BookDTO> find(BookDTO bookDTO, Pageable pageRequest) {
        Book filter = modelMapper.map(bookDTO, Book.class);
        Page<Book> result = bookService.find(filter, pageRequest);
        List<BookDTO> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, BookDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<BookDTO>(list, pageRequest, result.getTotalElements());
    }

    @GetMapping("{id}/loans")
    public Page<LoanDTO> loansByBook( @PathVariable Long id, Pageable pageable){
        Book book = bookService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Page<Loan> result = loanService.getLoansByBook(book, pageable);
        List<LoanDTO> list = result.getContent()
                .stream()
                .map(loan -> {
                    Book loanBook = loan.getBook();
                    BookDTO bookDTO = modelMapper.map(loanBook, BookDTO.class);
                    LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
                    loanDTO.setBook(bookDTO);
                    return loanDTO;
                }).collect(Collectors.toList());
        return new PageImpl<LoanDTO>(list, pageable, result.getTotalElements());
    }

}