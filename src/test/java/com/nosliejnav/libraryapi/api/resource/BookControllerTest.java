package com.nosliejnav.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosliejnav.libraryapi.api.dto.BookDTO;
import com.nosliejnav.libraryapi.exception.BusinessException;
import com.nosliejnav.libraryapi.model.entity.Book;
import com.nosliejnav.libraryapi.service.BookService;
import com.nosliejnav.libraryapi.service.LoanService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Com a versão antiga//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

        static String BOOK_API = "/api/books";

        @Autowired
        MockMvc mvc;

        @MockitoBean
        BookService bookService;

        @MockitoBean
        LoanService loanService;

        @Test
        @DisplayName("Deve criar um livro com sucesso.")
        public void createBookTest() throws Exception {

                BookDTO bookDTO = createNewBook();
                Book savedBook = Book.builder().id(10L).author("Artur").title("As aventuras").isbn("001").build();

                BDDMockito.given(bookService.save(Mockito.any(Book.class))).willReturn(savedBook);
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
                                .andExpect(jsonPath("isbn").value(bookDTO.getIsbn()));
        }

        @Test
        @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do livro.")
        public void createInvalidBookTest() throws Exception {

                String json = new ObjectMapper().writeValueAsString(new BookDTO());

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .post(BOOK_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json);

                // ERRO //
                mvc.perform(request)
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("errors", hasSize(3)));
        }

        @Test
        @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn já utilizando por outro.")
        public void createBookWithDuplicatedIsbn() throws Exception {

                BookDTO bookDTO = createNewBook();
                String json = new ObjectMapper().writeValueAsString(bookDTO);
                String mensagemErro = "Isbn já cadastrado.";
                BDDMockito.given(bookService.save(Mockito.any(Book.class)))
                                .willThrow(new BusinessException(mensagemErro));

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .post(BOOK_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc
                                .perform(request)
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("errors", hasSize(1)))
                                .andExpect(jsonPath("errors[0]").value(mensagemErro));
        }

        @Test
        @DisplayName("Deve obter informacoes de um livro.")
        public void getBookDetailsTest() throws Exception {
                // cenario (given)
                Long id = 1L;

                Book book = Book.builder()
                                .id(id)
                                .title(createNewBook().getTitle())
                                .author(createNewBook().getAuthor())
                                .isbn(createNewBook().getIsbn())
                                .build();

                BDDMockito.given(bookService.getById(id)).willReturn(Optional.of(book));

                // execucao (when)
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .get(BOOK_API.concat("/" + id))
                                .accept(MediaType.APPLICATION_JSON);
                mvc
                                .perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("id").value(id))
                                .andExpect(jsonPath("title").value(createNewBook().getTitle()))
                                .andExpect(jsonPath("author").value(createNewBook().getAuthor()))
                                .andExpect(jsonPath("isbn").value(createNewBook().getIsbn()));
        }

        @Test
        @DisplayName("Deve retornar resource not found quando o livro procurado não existir.")
        public void bookNotFoundTest() throws Exception {

                BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.empty());

                // execucao (when)
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .get(BOOK_API.concat("/" + 1))
                                .accept(MediaType.APPLICATION_JSON);

                mvc
                                .perform(request)
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Deve deletar um livro.")
        public void deleteBookTest() throws Exception {

                BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.of(Book.builder().id(1l).build()));

                // /api/boos/1
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .delete(BOOK_API.concat("/" + 1));

                mvc
                                .perform(request)
                                .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Deve retornar resource not found quando não encontrar o livro para deletar.")
        public void deleteInexistenteBookTest() throws Exception {

                BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.empty());

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .delete(BOOK_API.concat("/" + 1));

                mvc
                                .perform(request)
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Deve atualizar um livro.")
        public void updateBookTest() throws Exception {

                Long id = 1l;
                String json = new ObjectMapper().writeValueAsString(createNewBook());

                Book updatingBook = Book.builder().id(1l).title("some title").author("some author").isbn("321").build();
                BDDMockito.given( bookService.getById(id) ).willReturn( Optional.of(updatingBook) );
                Book updatedBook = Book.builder().id(id).author("Artur").title("As aventuras").isbn("321").build();
                BDDMockito.given(bookService.update(updatingBook)).willReturn(updatedBook);

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                        .put(BOOK_API.concat("/" + 1))
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON);

                mvc.perform( request )
                        .andExpect( status().isOk() )
                        .andExpect( jsonPath("id").value(id) )
                        .andExpect( jsonPath("title").value(createNewBook().getTitle()) )
                        .andExpect( jsonPath("author").value(createNewBook().getAuthor()) )
                        .andExpect( jsonPath("isbn").value("321") );
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar atualizar um livro inexistente.")
        public void updateInexistenteBookTest() throws Exception {

                String json = new ObjectMapper().writeValueAsString(createNewBook());
                BDDMockito.given(bookService.getById(Mockito.anyLong()))
                                .willReturn(Optional.empty());

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .put(BOOK_API.concat("/" + 1))
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON);

                mvc
                                .perform(request)
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Deve filtrar livros.")
        public void findBooksTest() throws Exception {

                Long id = 1l;

                Book book = Book.builder()
                                .id(id)
                                .title(createNewBook().getTitle())
                                .author(createNewBook().getAuthor())
                                .isbn(createNewBook().getIsbn())
                                .build();

                BDDMockito.given(bookService.find(Mockito.any(Book.class), Mockito.any(Pageable.class)))
                                .willReturn(new PageImpl<Book>(Arrays.asList(book), PageRequest.of(0, 100), 1));

                String queryString = String.format("?title=%s&author=%s&page=0&size=100",
                                book.getTitle(), book.getAuthor());

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .get(BOOK_API.concat(queryString))
                                .accept(MediaType.APPLICATION_JSON);

                mvc
                                .perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("content", Matchers.hasSize(1)))
                                .andExpect(jsonPath("totalElements").value(1))
                                .andExpect(jsonPath("pageable.pageSize").value(100))
                                .andExpect(jsonPath("pageable.pageNumber").value(0));
        }

        private BookDTO createNewBook() {
                return BookDTO.builder().author("Artur").title("As aventuras").isbn("001").build();
        }
}