package com.nosliejnav.libraryapi.api.resource;

import com.nosliejnav.libraryapi.api.dto.BookDTO;
import com.nosliejnav.libraryapi.model.entity.Book;
import com.nosliejnav.libraryapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO dto){
        Book entity =
                Book.builder()
                        .author(dto.getAuthor())
                        .title(dto.getTitle())
                        .isbn(dto.getIsbn())
                        .build();
        entity = bookService.save(entity);
        return BookDTO.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .build();
    }
}
