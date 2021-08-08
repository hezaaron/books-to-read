package bookstoread;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

@DisplayName("A bookShelf")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init(Map<String, Book> books) {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
    }

    BookShelfSpec(TestInfo testInfo) {
        System.out.println("Working on test " + testInfo.getDisplayName());
    }

    @Nested @DisplayName("is empty")
    class IsEmpty {

        @Test @DisplayName("is empty when no book is added to it")
        void shelfEmptyWhenNoBookAdded() {
            List<Book> books = shelf.books();

            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }

        @Test @DisplayName("remains empty when addBooks is called without books")
        void emptyBookShelfWhenAddBooksIsCalledWithoutBooks() {
            shelf.addBooks();

            List<Book> books = shelf.books();

            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }

    }

    @Nested @DisplayName("after adding books")
    class BooksAreAdded {

        @Test @DisplayName("contains two books when two books are added")
        void shelfContainsTwoBooksWhenTwoBooksAdded() {
            shelf.addBooks(effectiveJava, codeComplete);

            List<Book> books = shelf.books();

            assertEquals(2, books.size(), () -> "BookShelf should have two books.");
        }

        @Test @DisplayName("returns an immutable books collection to client")
        void booksReturnedFromBookShelfIsImmutableForClient() {
            shelf.addBooks(effectiveJava, codeComplete);

            List<Book> books = shelf.books();

            try {
                books.add(mythicalManMonth);
                fail(() -> "Should not be able to add book to books");
            } catch (Exception e) {
                assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnSupportedOperationException.");
            }
        }

    }
}
