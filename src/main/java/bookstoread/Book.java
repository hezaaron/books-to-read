package bookstoread;

import java.time.LocalDate;

public class Book implements Comparable<Book> {

    private final String title;
    private final String author;
    private final LocalDate publishedOn;
    private LocalDate startedReadingOn;
    private LocalDate finishedReadingOn;

    public Book(String title, String author, LocalDate publishedOn) {
        this.title = title;
        this.author = author;
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public LocalDate getPublishedOn() {
        return this.publishedOn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedOn=" + publishedOn +
                '}';
    }

    public void startedReadingOn(LocalDate startedReadingOn) {
        this.startedReadingOn = startedReadingOn;
    }

    public void finishedReadingOn(LocalDate finishedReadingOn) {
        this.finishedReadingOn = finishedReadingOn;
    }

    public boolean isRead() {
        return startedReadingOn != null && finishedReadingOn != null;
    }

    @Override
    public int compareTo(Book that) {
        return this.title.compareTo(that.title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Book that = (Book) obj;
        if (this.title == null) {
            if (that.title != null) return false;
        } else if (!title.equals(that.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        return prime * result + ((this.title == null) ? 0 : title.hashCode());
    }
}