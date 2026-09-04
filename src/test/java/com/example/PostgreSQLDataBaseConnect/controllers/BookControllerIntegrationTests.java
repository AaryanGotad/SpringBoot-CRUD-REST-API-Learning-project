package com.example.PostgreSQLDataBaseConnect.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.PostgreSQLDataBaseConnect.TestDataUtil;
import com.example.PostgreSQLDataBaseConnect.domain.dto.BookDto;
import com.example.PostgreSQLDataBaseConnect.domain.entities.BookEntity;
import com.example.PostgreSQLDataBaseConnect.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
// @ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(createBookJson)                                  
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(createBookJson)                                  
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200Ok() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books")
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    // @Test
    // public void testThatListBooksReturnsBook() throws Exception {
    //     BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    //     bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

    //     mockMvc.perform(
    //         MockMvcRequestBuilders.get("/books")
    //                               .contentType(MediaType.APPLICATION_JSON)      
    //     ).andExpect(
    //         MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBookEntity.getIsbn())
    //     ).andExpect(
    //         MockMvcResultMatchers.jsonPath("$[0].title").value(testBookEntity.getTitle())
    //     );
    // }

    @Test
    public void testThatGetBooksReturnsHttpStatus200OkWhenBookExists() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/"+ testBookEntity.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBooksReturnsHttpStatus404WhenBookDoesntExists() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/"+ testBookEntity.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200Ok() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBookEntity.getIsbn());

        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );           
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBookEntity.getIsbn());
        testBookA.setTitle("UPDATED");

        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );           
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200Ok() throws Exception{
        BookEntity testBook = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBook.getIsbn(), testBook);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setTitle("UPDATED");
        String bookDtoJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(bookDtoJson)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        ); 
    }

    @Test
    public void testThatPartialUpdateBookReturnsBook() throws Exception{
        BookEntity testBook = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBook.getIsbn(), testBook);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setTitle("UPDATED");
        String bookDtoJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(bookDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(savedBook.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        ); 
    }

    @Test
    public void testThatDeleteBookReturnsHttpsStatus204ForNonExistingBook() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/books/987-524-56-4")
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteBookReturnsHttpsStatus204ForAnExistingBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/books/" + savedBook.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
        );
    }
}
