package v3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library implements Externalizable {
    private static final long serialVersionUID = 1L;

    private String      name;
    private List<Book>  books;
    private List<Reader> readers;

    public Library() {
        this.books   = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public Library(String name) {
        this.name    = name;
        this.books   = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    // Бізнес-методи

    public void addBook(Book book)         { books.add(book); }
    public void registerReader(Reader r)   { readers.add(r); }

    public boolean lendBook(Reader reader, Book book) {
        if (books.contains(book)) {
            books.remove(book);
            reader.borrowBook(book);
            System.out.println("  Book " + book.getTitle() + " issued to reader " + reader.getLastName());
            return true;
        }
        System.out.println("  Book \"" + book.getTitle() + "\" is not available in the collection!");
        return false;
    }

    public boolean returnBook(Reader reader, Book book) {
        if (reader.returnBook(book)) {
            books.add(book);
            System.out.println("  Reader " + reader.getLastName() + " returned book " + book.getTitle());
            return true;
        }
        System.out.println("  Reader " + reader.getLastName() + " does not have book \"" + book.getTitle() + "\"");
        return false;
    }

    // Externalizable

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);

        out.writeInt(books.size());
        for (Book b : books) {
            b.writeExternal(out);
        }

        out.writeInt(readers.size());
        for (Reader r : readers) {
            r.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();

        int bookCount = in.readInt();
        books = new ArrayList<>(bookCount);
        for (int i = 0; i < bookCount; i++) {
            Book b = new Book();
            b.readExternal(in);
            books.add(b);
        }

        int readerCount = in.readInt();
        readers = new ArrayList<>(readerCount);
        for (int i = 0; i < readerCount; i++) {
            Reader r = new Reader();
            r.readExternal(in);
            readers.add(r);
        }
    }

    // Getters та Setters

    public String      getName()    { return name;    }
    public void        setName(String n) { this.name = n; }

    public List<Book>  getBooks()   { return books;   }
    public void        setBooks(List<Book> b)  { this.books = b; }

    public List<Reader> getReaders(){ return readers; }
    public void        setReaders(List<Reader> r){ this.readers = r; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Library: ").append(name).append("\n");
        sb.append("Book collection (").append(books.size()).append("):\n");
        for (Book b : books)     sb.append("  - ").append(b).append("\n");
        sb.append("Readers (").append(readers.size()).append("):\n");
        for (Reader r : readers) sb.append("  - ").append(r).append("\n");
        return sb.toString();
    }
}
