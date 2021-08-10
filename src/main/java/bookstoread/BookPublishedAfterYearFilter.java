package bookstoread;

import java.time.LocalDate;

public class BookPublishedAfterYearFilter implements BookFilter {

    private LocalDate startedDate;

    static BookPublishedAfterYearFilter after(int year) {
        BookPublishedAfterYearFilter filter = new BookPublishedAfterYearFilter();
        filter.startedDate = LocalDate.of(year, 12,31);
        return filter;
    }

    @Override
    public boolean apply(final Book book) {
        return book.getPublishedOn().isAfter(startedDate);
    }
}
