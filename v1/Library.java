package v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Book> books;
    private List<Reader> readers;

    public Library() {
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerReader(Reader reader) {
        readers.add(reader);
    }

    // Видати книгу читачеві (якщо є в наявності)

    public boolean lendBook(Reader reader, Book book) {
        if (books.contains(book)) {
            books.remove(book);
            reader.borrowBook(book);
            System.out.println("  Book " + book.getTitle() + " was given to the reader " + reader.getLastName());
            return true;
        }
        System.out.println("  Book \"" + book.getTitle() + "\" is not available!");
        return false;
    }

    // Повернути книгу до бібліотеки

    public boolean returnBook(Reader reader, Book book) {
        if (reader.returnBook(book)) {
            books.add(book);
            System.out.println("  The customer " + reader.getLastName() + " returned the book " + book.getTitle());
            return true;
        }
        System.out.println("  The customer " + reader.getLastName() + " does not have the book \"" + book.getTitle() + "\"");
        return false;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

    public List<Reader> getReaders() { return readers; }
    public void setReaders(List<Reader> readers) { this.readers = readers; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Library: ").append(name).append("\n");
        sb.append("Book Collection (").append(books.size()).append("):\n");
        for (Book b : books) sb.append("  - ").append(b).append("\n");
        sb.append("The customers (").append(readers.size()).append("):\n");
        for (Reader r : readers) sb.append("  - ").append(r).append("\n");
        return sb.toString();
    }
}
