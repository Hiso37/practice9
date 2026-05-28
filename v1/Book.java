package v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private List<Author> authors;
    private int year;
    private int edition;

    public Book() {
        this.authors = new ArrayList<>();
    }

    public Book(String title, int year, int edition) {
        this.title = title;
        this.year = year;
        this.edition = edition;
        this.authors = new ArrayList<>();
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Author> getAuthors() { return authors; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getEdition() { return edition; }
    public void setEdition(int edition) { this.edition = edition; }

    @Override
    public String toString() {
        return "\"" + title + "\" (authors: " + authors + ", year: " + year + ", ed. " + edition + ")";
    }
}
