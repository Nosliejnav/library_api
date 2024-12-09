package com.nosliejnav.libraryapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String isbn;


}
