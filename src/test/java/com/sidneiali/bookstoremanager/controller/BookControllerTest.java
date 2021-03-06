package com.sidneiali.bookstoremanager.controller;

import com.sidneiali.bookstoremanager.dto.BookDTO;
import com.sidneiali.bookstoremanager.dto.MessageResponseDTO;
import com.sidneiali.bookstoremanager.service.BookService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.sidneiali.bookstoremanager.utils.BookUtils.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    public static final String BOOK_API_URL_PATH = "/api/v1/books";
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void testWhenPostIsCalledThenABookShouldBeCreated() throws Exception {
        BookDTO bookDTO = createFakeBookDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO
                .builder()
                .message("Book created with ID " + bookDTO.getId())
                .build();

        when(bookService.create(bookDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Is.is(expectedMessageResponse.getMessage())));
    }



    @Test
    void testWhenPostIsCalledThenABookShouldBeReturned() throws Exception {
        BookDTO bookDTO = createFakeBookDTO();
        bookDTO.setIsbn("invalid isbn");

        mockMvc.perform(post(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }
}
