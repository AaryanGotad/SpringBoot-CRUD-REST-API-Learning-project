package com.example.PostgreSQLDataBaseConnect;

import com.example.PostgreSQLDataBaseConnect.domain.dto.AuthorDto;
import com.example.PostgreSQLDataBaseConnect.domain.dto.BookDto;
import com.example.PostgreSQLDataBaseConnect.domain.entities.AuthorEntity;
import com.example.PostgreSQLDataBaseConnect.domain.entities.BookEntity;


// contains all test utility functions
public final class TestDataUtil {
    private TestDataUtil() {}

    public static AuthorEntity createTestAuthorEntityA() {
        return AuthorEntity.builder()
                        .name("Abigail Rose")
                        .age(80)
                        .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                    .name("Abigail Rose")
                    .age(80)
                    .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .name("Jesse A. Casey")
                .age(24)
                .build();
    }

    public static BookEntity createTestBookEntityA(final AuthorEntity author) {
        return BookEntity.builder()
                    .isbn("978-1-2345-6789-0")
                    .title("The Shadow in the Attic")
                    .author(author) // passing in the author object instead of author_id
                    .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                    .isbn("978-1-2345-6789-0")
                    .title("The Shadow in the Attic")
                    .author(author) // passing in the author object instead of author_id
                    .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity author) {
        return BookEntity.builder()
                    .isbn("978-1-2345-6789-1")
                    .title("Beyond the Horizon")
                    .author(author)
                    .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity author) {
        return BookEntity.builder()
                    .isbn("978-1-2345-6789-2")
                    .title("The Lost Ember")
                    .author(author)
                    .build();
    }

}
