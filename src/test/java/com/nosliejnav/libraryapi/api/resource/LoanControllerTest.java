package com.nosliejnav.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosliejnav.libraryapi.api.dto.BookDTO;
import com.nosliejnav.libraryapi.api.dto.LoanDTO;
import com.nosliejnav.libraryapi.api.dto.ReturnedLoanDTO;
import com.nosliejnav.libraryapi.api.exception.BusinessException;
import com.nosliejnav.libraryapi.model.entity.Book;
import com.nosliejnav.libraryapi.model.entity.Loan;
import com.nosliejnav.libraryapi.service.BookService;
import com.nosliejnav.libraryapi.service.LoanService;

import lombok.val;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = LoanController.class)
public class LoanControllerTest {

        static final String LOAN_API = "/api/loans";

        @Autowired
        MockMvc mvc;

        @MockitoBean
        private BookService bookService;

        @MockitoBean
        private LoanService loanService;

        @Test
        @DisplayName("Deve realizar um emprestimo")
        public void createLoanTest() throws Exception {

                LoanDTO dto = LoanDTO.builder().isbn("123").customer("Fulano").build();
                String json = new ObjectMapper().writeValueAsString(dto);

                Book book = Book.builder().id(1l).isbn("123").build();
                BDDMockito.given(bookService.getBookByIsbn("123")).willReturn(Optional.of(book));

                Loan loan = Loan.builder().id(1l).customer("Fulano").book(book).loanDate(LocalDate.now()).build();
                BDDMockito.given(loanService.save(Mockito.any(Loan.class))).willReturn(loan);

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LOAN_API)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request)
                                .andExpect(status().isCreated())
                                .andExpect(MockMvcResultMatchers.content().string("1"));
        }

        @Test
        @DisplayName("Deve retornar erro ao tentar fazer emprestimo de um livro inexistente.")
        public void invalidIsbnCreateLoanTest() throws Exception {

                LoanDTO dto = LoanDTO.builder().isbn("123").customer("Fulano").build();
                String json = new ObjectMapper().writeValueAsString(dto);

                BDDMockito.given(bookService.getBookByIsbn("123")).willReturn(Optional.empty());

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LOAN_API)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                                .andExpect(jsonPath("errors[0]").value("Book not found for passed isbn"));
        }

        @Test
        @DisplayName("Deve retornar erro ao tentar fazer emprestimo de um livro emprestado.")
        public void loanedBookErrorOnCreateLoanTest() throws Exception {

                LoanDTO dto = LoanDTO.builder().isbn("123").customer("Fulano").build();
                String json = new ObjectMapper().writeValueAsString(dto);

                Book book = Book.builder().id(1l).isbn("123").build();
                BDDMockito.given(bookService.getBookByIsbn("123")).willReturn(Optional.of(book));

                BDDMockito.given(loanService.save(Mockito.any(Loan.class)))
                                .willThrow(new BusinessException("Book already loaned"));

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(LOAN_API)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                                .andExpect(jsonPath("errors[0]").value("Book not found for passed isbn"));
        }

        @Test
        @DisplayName("Deve retornar um livro.")
        public void returnBookTest() throws Exception {
                // cenario { returned: true }
                ReturnedLoanDTO dto = ReturnedLoanDTO.builder().returned(true).build();
                Loan loan = Loan.builder().id(1l).build();
                BDDMockito.given(loanService.getById(Mockito.anyLong()))
                                .willReturn(Optional.of(loan));

                String json = new ObjectMapper().writeValueAsString(dto);

                mvc.perform(
                                patch(LOAN_API.concat("/1"))
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(json))
                                .andExpect(status().isOk());

                Mockito.verify(loanService, Mockito.times(1)).update(loan);
        }

}

// private BookDTO createNewBook() {
// return BookDTO.builder().author("Artur").title("As
// aventuras").isbn("001").build();
