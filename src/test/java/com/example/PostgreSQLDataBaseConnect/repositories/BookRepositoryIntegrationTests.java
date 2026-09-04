package com.example.PostgreSQLDataBaseConnect.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.PostgreSQLDataBaseConnect.TestDataUtil;
import com.example.PostgreSQLDataBaseConnect.domain.entities.AuthorEntity;
import com.example.PostgreSQLDataBaseConnect.domain.entities.BookEntity;
import com.example.PostgreSQLDataBaseConnect.respositories.AuthorRepository;
import com.example.PostgreSQLDataBaseConnect.respositories.BookRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;
    private AuthorRepository authorRepo;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepo) {
        this.underTest = underTest;
        this.authorRepo = authorRepo;
    }

    // Test to see if we can create a book and find it using the find() method
    @Test
    public void testThatBookCanBeCreatedAndRecalled() {

        // creating and persisting the Author first
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        // No need to save the author in the DB, as we have our cascade setup in the book repo 
        // authorRepo.save(author); // ensures author_id exists in the authors table (to get passed the foreign key constraint of author_id column in Books table)

        // creating the book referencing to the saved author's id
        BookEntity book = TestDataUtil.createTestBookEntityA(author);

        // now inserting the book (foreign key check will pass)
        underTest.save(book);
        
        // Now verifying book recall
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get().getIsbn()).isEqualTo(book.getIsbn());
        assertThat(result.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(result.get().getAuthor().getName()).isEqualTo(author.getName());
        assertThat(result.get().getAuthor().getAge()).isEqualTo(author.getAge());
        assertThat(result.get().getAuthor().getId()).isNotNull();
    }

    @Test
    public void testThatMulitpleBooksCanBeCreatedAndRecalled() {
        // creating and persisting the author first
        // I will be creating 3 books belonging to the same author
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();

        BookEntity bookA = TestDataUtil.createTestBookEntityA(author);
        underTest.save(bookA);

        BookEntity bookB = TestDataUtil.createTestBookB(author);
        underTest.save(bookB);

        BookEntity bookC = TestDataUtil.createTestBookC(author);
        underTest.save(bookC);

        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).allSatisfy(book -> {
            assertThat(book.getAuthor().getId()).isNotNull();
            assertThat(book.getAuthor().getName()).isEqualTo(author.getName());
            assertThat(book.getAuthor().getAge()).isEqualTo(author.getAge());
        });
        assertThat(result).extracting(BookEntity::getIsbn)
            .containsExactlyInAnyOrder(bookA.getIsbn(), bookB.getIsbn(), bookC.getIsbn());
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        
        BookEntity book = TestDataUtil.createTestBookEntityA(author);
        underTest.save(book);

        book.setTitle("UPDATED");

        underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();

        assertThat(result.get().getAuthor().getId()).isNotNull();
        assertThat(result.get().getAuthor().getName()).isEqualTo(author.getName());
        assertThat(result.get().getAuthor().getAge()).isEqualTo(author.getAge());
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();

        BookEntity book = TestDataUtil.createTestBookEntityA(author);
        underTest.save(book);

        underTest.deleteById(book.getIsbn());

        Optional<BookEntity> result = underTest.findById(book.getIsbn());

        assertThat(result).isEmpty();
    }

}
