package bookstoread;

import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BookShelf {
    private static final Logger LOGGER = Logger.getLogger(BookShelf.class.getName());

    private final List<Book> books = new ArrayList<>();

    public List<Book> books() {
        return Collections.unmodifiableList(books);
    }

    public void addBooks(Book... booksToAdd) {
        Arrays.stream(booksToAdd).forEach(books::add);
    }

    public List<Book> arrange(Comparator<Book> criteria) {
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public List<Book> arrange() {
        return arrange(Comparator.naturalOrder());
    }

    public <K> Map<K, List<Book>> groupBy(Function<Book, K> fx) {
        return books.stream().collect(Collectors.groupingBy(fx));
    }

    public Map<Year, List<Book>> groupByPublicationYear() {
        return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
    }

    public Map<String, List<Book>> groupByAuthor() {
        return groupBy(Book::getAuthor);
    }

    public Progress progress() {
        final int booksRead = (int) books.stream().filter(Book::isRead).count();
        final int booksToRead = books.size() - booksRead;
        int percentageCompleted;
        int percentageToRead;

        try {
            percentageCompleted = booksRead * 100 / books.size();
            percentageToRead = booksToRead * 100 / books.size();
        } catch (ArithmeticException ae) {
            LOGGER.log(Level.FINE, String.format("%s, %s", ae.toString(), "; Your BookShelf is empty, add some books!"));
            return new Progress(0, 0, 0);
        }

        return new Progress(percentageCompleted, percentageToRead, 0);
    }

    public List<Book> findBooksByTitle(String title) {
        return books.stream().filter(b -> b.getTitle().toLowerCase().contains(title))
                             .collect(Collectors.toList());
    }

    public List<Book> findBooksByTitle(String title, BookFilter filter) {
        return findBooksByTitle(title).stream().filter(filter::apply)
                                      .collect(Collectors.toList());
    }
}
