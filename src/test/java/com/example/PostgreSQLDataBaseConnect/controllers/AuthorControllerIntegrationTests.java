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
import com.example.PostgreSQLDataBaseConnect.domain.dto.AuthorDto;
import com.example.PostgreSQLDataBaseConnect.domain.entities.AuthorEntity;
import com.example.PostgreSQLDataBaseConnect.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private MockMvc mockMvc;

    private AuthorService authorService;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorJson)                                  
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );   
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorJson)                                  
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(80)
        );   
    }

    @Test
    public void testThatListAuthorsReturnesHttpStatus200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors")                                  
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnesListOfAuthors() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.save(testAuthorEntityA);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors")                                  
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );
    }

    @Test
    public void testThatListAuthorsReturnesHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.save(testAuthorEntityA);
        
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/1")                                  
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnesHttpStatus404WhenNoAuthorExists() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/1")                                  
        ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatListAuthorsReturnesAuthorWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.save(testAuthorEntityA);
        
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/1")                                  
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnesHttpStatus404WhenNoAuthorDoesntExists() throws Exception {
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);
        
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/99")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorDtoJson)                                  
        ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnesHttpStatus200WhenNoAuthorExists() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);
        
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/" + savedAuthor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorDtoJson)                                  
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
        AuthorEntity tesAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(tesAuthorEntityA);

        AuthorEntity authorDto = TestDataUtil.createTestAuthorB();
        authorDto.setId(savedAuthor.getId());

        String authorDtoUpdateJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorDtoUpdateJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus200Ok() throws Exception {
        AuthorEntity tesAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(tesAuthorEntityA);
        
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        testAuthorDto.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);
        
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(authorDtoJson)                                  
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsAuthor() throws Exception {
        AuthorEntity tesAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(tesAuthorEntityA);
        
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        testAuthorDto.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);
        
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(authorDtoJson)                                  
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDto.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpsStatus204ForNonExistingAuthor() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/authors/999")
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
        );                     
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpsStatus204ForAnExistingAuthor() throws Exception {
        AuthorEntity tesAuthorEntity = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(tesAuthorEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
        );
    }
}
