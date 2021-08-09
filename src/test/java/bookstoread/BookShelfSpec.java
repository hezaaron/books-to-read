package bookstoread;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Nested @DisplayName("after arranging books")
    class BooksAreArranged {

        @Disabled("Needs to implement Comparator")
        @Test @DisplayName("books are arranged lexicographically by book title")
        void bookshelfArrangeByBookTitle() {
            shelf.addBooks(effectiveJava, codeComplete, mythicalManMonth);

            List<Book> books = shelf.arrange();

            assertThat(books).isSorted().as(() -> "Books in a bookshelf should be arranged lexicographically by book title");
        }

        @Test @DisplayName("books are in insertion order after calling arrange method")
        void booksInBookShelfAreInInsertionOrderAfterCallingArange() {
            shelf.addBooks(effectiveJava, codeComplete, mythicalManMonth);
            shelf.arrange();

            List<Book> books = shelf.books();

            assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth),
                    books, () -> "Books in bookshelf are in insertion order");
        }

    }

    @Nested @DisplayName("after grouping books")
    class BooksAreGrouped {

        @Test @DisplayName("books are grouped according to user provided criteria")
        void bookshelfArrangedByUserProvidedCriteria() {
            shelf.addBooks(effectiveJava, codeComplete, mythicalManMonth);
            Comparator<Book> reversedOrder = Comparator.<Book>naturalOrder().reversed();

            List<Book> books = shelf.arrange(reversedOrder);

            assertThat(books).isSortedAccordingTo(reversedOrder);
        }

        @Test @DisplayName("books are grouped by publication year")
        void groupBooksInsideBookShelfByPublicationYear() {
            shelf.addBooks(effectiveJava, codeComplete, mythicalManMonth, cleanCode);

            Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

            assertThat(booksByPublicationYear).containsKey(Year.of(2008))
                    .containsValues(Arrays.asList(effectiveJava, cleanCode));
            assertThat(booksByPublicationYear).containsKey(Year.of(2004))
                    .containsValues(Collections.singletonList(codeComplete));
            assertThat(booksByPublicationYear).containsKey(Year.of(1975))
                    .containsValues(Collections.singletonList(mythicalManMonth));
        }

        @Test @DisplayName("books inside bookshelf are grouped according to user provided criteria(group by author name)")
        void groupBooksByUserProvidedCriteria() {
            shelf.addBooks(effectiveJava, codeComplete, mythicalManMonth, cleanCode);

            Map<String, List<Book>> booksByAuthor = shelf.groupByAuthor();

            assertThat(booksByAuthor).containsKey("Joshua Bloch")
                    .containsValue(Collections.singletonList(effectiveJava));
            assertThat(booksByAuthor).containsKey("Steve McConnel")
                    .containsValue(Collections.singletonList(codeComplete));
            assertThat(booksByAuthor).containsKey("Frederick Phillips Brooks")
                    .containsValue(Collections.singletonList(mythicalManMonth));
            assertThat(booksByAuthor).containsKey("Robert C. Martin")
                    .containsValue(Collections.singletonList(cleanCode));
        }

    }

}
