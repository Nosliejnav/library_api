package com.nosliejnav.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosliejnav.libraryapi.api.dto.BookDTO;
import com.nosliejnav.libraryapi.api.exception.BusinessException;
import com.nosliejnav.libraryapi.model.entity.Book;
import com.nosliejnav.libraryapi.service.BookService;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Com a versão antiga//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mvc;

    @MockitoBean
    BookService bookService;

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    public void createBookTest() throws Exception {

        BookDTO bookDTO = CreateNewBook();
        Book savedBook = Book.builder().id(10L).author("Artur").title("As aventuras").isbn("001").build();

        BDDMockito.given(bookService.save(Mockito.any( Book.class))).willReturn(savedBook);
        String json = new ObjectMapper().writeValueAsString(bookDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                    .perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").value(10l))
                    .andExpect(jsonPath("title").value(bookDTO.getTitle()))
                    .andExpect(jsonPath("author").value(bookDTO.getAuthor()))
                    .andExpect(jsonPath("isbn").value(bookDTO.getIsbn()))

        ;
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do livro.")
    public void createInvalidBookTest() throws Exception{

        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform( request)
                .andExpect(status().isBadRequest())
                .andExpect( jsonPath("errors", hasSize(3)));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn já utilizando por outro.")
    public void createBookWithDuplicatedIsbn() throws Exception {

        BookDTO bookDTO = CreateNewBook();
        String json = new ObjectMapper().writeValueAsString( bookDTO);
        String mensagemErro = "Isbn já cadastrado.";
        BDDMockito.given(bookService.save(Mockito.any(Book.class)))
                .willThrow(new BusinessException(mensagemErro));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform( request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(mensagemErro));
    }
    private BookDTO CreateNewBook() {
        return BookDTO.builder().author("Artur").title("As aventuras").isbn("001").build();
    }
}
