package bookstoread;

import java.time.LocalDate;

public class BookPublishedBeforeYearFilter implements BookFilter {

    private LocalDate endDate;

    static BookPublishedBeforeYearFilter before(int year) {
        BookPublishedBeforeYearFilter filter = new BookPublishedBeforeYearFilter();
        filter.endDate = LocalDate.of(year, 12, 31);
        return filter;
    }

    @Override
    public boolean apply(final Book book) {
        return book.getPublishedOn().isBefore(endDate);
    }
}
