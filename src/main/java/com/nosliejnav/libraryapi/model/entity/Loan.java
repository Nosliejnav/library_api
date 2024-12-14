package com.nosliejnav.libraryapi.model.entity;

import lombok.Builder;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Builder
@Entity
@Table
public class Loan {

    private Long id;

    private String customer;

    private Book book;

    private LocalDate loanDate;

    private Boolean returned;

    public Loan(Long id, String customer, Book book, LocalDate loanDate, Boolean returned) {
        this.id = id;
        this.customer = customer;
        this.book = book;
        this.loanDate = loanDate;
        this.returned = returned;
    }

    public Loan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

}
