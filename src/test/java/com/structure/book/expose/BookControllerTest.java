package com.structure.book.expose;

import com.structure.book.business.service.BookService;
import com.structure.book.expose.controller.BookController;
import com.structure.book.expose.dto.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.UUID;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;


    private UUID bookId;
    private BookDto.Request bookRequest;
    private BookDto.Response bookResponse;


    @TestConfiguration
    static class BookServiceTestConfiguration {

        @Bean
        @Primary
        public BookService bookServiceMock() {
            return mock(BookService.class);
        }

    }

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();

        bookRequest = new BookDto.Request();
        bookRequest.setName("Book Name");
        bookRequest.setCode("Book Code");
        bookRequest.setIpsCode("Book IPS Code");
        bookRequest.setState(true);

        bookResponse = new BookDto.Response();
        bookResponse.setId(bookId);
        bookResponse.setName("Book Name");
        bookResponse.setCode("Book Code");
        bookResponse.setIpsCode("Book IPS Code");
        bookResponse.setState(true);
    }

    @Test
    void testGetAll() throws Exception {
        List<BookDto.Response> bookList = List.of(bookResponse);
        when(bookService.getAll()).thenReturn(bookList);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(bookId)).thenReturn(bookResponse);
        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(bookId.toString()))
                .andExpect(jsonPath("$.name").value("Book Name"))
                .andExpect(jsonPath("$.code").value("Book Code"))
                .andExpect(jsonPath("$.ipsCode").value("Book IPS Code"))
                .andExpect(jsonPath("$.state").value(true));
    }

    @Test
    void testSave() throws Exception {
        when(bookService.save(any(BookDto.Request.class))).thenReturn(bookResponse);
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Book Name")))
                .andExpect(jsonPath("$.code", is("Book Code")));
        ;
    }

    @Test
    void testUpdate() throws Exception {
        when(bookService.update(any(UUID.class), any(BookDto.Request.class))).thenReturn(bookResponse);
        mockMvc.perform(put("/books/{id}", bookId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book Name"))
                .andExpect(jsonPath("$.code").value("Book Code"))
                .andExpect(jsonPath("$.ipsCode").value("Book IPS Code"))
                .andExpect(jsonPath("$.state").value(true));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isNoContent());
    }
}
