package com.nosliejnav.libraryapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnedLoanDTO {

    private Boolean returned;


}
