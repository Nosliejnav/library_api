package com.nosliejnav.libraryapi.api.dto;

import lombok.Builder;

@Builder
public class ReturnedLoanDTO {

    private Boolean returned;

    public ReturnedLoanDTO(Boolean returned) {
        this.returned = returned;
    }

    public ReturnedLoanDTO() {
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

}
