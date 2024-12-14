package com.nosliejnav.libraryapi.api.dto;

import lombok.Builder;

@Builder
public class LoanDTO {

    private String isbn;
    private String customer;

    public LoanDTO(String isbn, String customer) {
        this.isbn = isbn;
        this.customer = customer;
    }

    public LoanDTO() {
    }

}
