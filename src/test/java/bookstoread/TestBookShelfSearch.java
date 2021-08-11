package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("A bookshelf search")
@ExtendWith(BooksParameterResolver.class)
class TestBookShelfSearch {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void init(Map<String, Book> books) {
        this.shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
        this.refactoring = books.get("Refactoring");
    }

    @BeforeEach
    void setup() {
        shelf.addBooks(codeComplete, effectiveJava, mythicalManMonth, cleanCode);
    }

    @Test @DisplayName(" should find books with title containing text")
    void shouldFindBooksWithTitleContainingText() {
        List<Book> books = shelf.findBooksByTitle("code");

        assertThat(books.size()).isEqualTo(2);
    }

    @Nested @DisplayName("book published date")
    class BookPublishedDateFilter {

        @Test @DisplayName(" should find books with title containing text and published after specified date")
        void shouldFilterSearchedBooksBasedOnPublishedDate() {
            List<Book> books = shelf.findBooksByTitle("code", b -> b.getPublishedOn().isBefore(LocalDate.of(2014, 12, 31)));

            assertThat(books.size()).isEqualTo(2);
        }

        @Test @DisplayName("is after specified year")
        void validateBookPublishedDatePostAskedYear() {
            BookFilter filter = BookPublishedAfterYearFilter.after(2007);

            assertTrue(filter.apply(cleanCode));
            assertFalse(filter.apply(codeComplete));
        }

    }

}
