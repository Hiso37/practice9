package v2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 Версія 2: Library реалізує Serializable.
 Поля books та readers мають тип, що НЕ є Serializable,
 тому вони позначені як transient та серіалізуються вручну.
*/

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    // Ці поля transient, бо Book і Reader — не Serializable

    private transient List<Book>   books;
    private transient List<Reader> readers;

    public Library() {
        this.books   = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public Library(String name) {
        this.name  = name;
        this.books   = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    // Бізнес-методи

    public void addBook(Book book) { books.add(book); }

    public void registerReader(Reader reader) { readers.add(reader); }

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

    // Ручне управління серіалізацією

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // серіалізує name

        // Серіалізуємо список книг вручну

        out.writeInt(books.size());
        for (Book b : books) {
            out.writeUTF(b.getTitle());
            out.writeInt(b.getYear());
            out.writeInt(b.getEdition());

            // Автори книги

            out.writeInt(b.getAuthors().size());
            for (Author a : b.getAuthors()) {
                out.writeUTF(a.getFirstName());
                out.writeUTF(a.getLastName());
            }
        }

        // Серіалізуємо список читачів вручну

        out.writeInt(readers.size());
        for (Reader r : readers) {
            out.writeUTF(r.getFirstName());
            out.writeUTF(r.getLastName());
            out.writeInt(r.getRegistrationNumber());
            // Книги на руках у читача
            out.writeInt(r.getBorrowedBooks().size());
            for (Book b : r.getBorrowedBooks()) {
                out.writeUTF(b.getTitle());
                out.writeInt(b.getYear());
                out.writeInt(b.getEdition());
                out.writeInt(b.getAuthors().size());
                for (Author a : b.getAuthors()) {
                    out.writeUTF(a.getFirstName());
                    out.writeUTF(a.getLastName());
                }
            }
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        // Відновлюємо список книг

        int bookCount = in.readInt();
        books = new ArrayList<>(bookCount);
        for (int i = 0; i < bookCount; i++) {
            books.add(readBook(in));
        }

        // Відновлюємо список читачів

        int readerCount = in.readInt();
        readers = new ArrayList<>(readerCount);
        for (int i = 0; i < readerCount; i++) {
            String firstName = in.readUTF();
            String lastName  = in.readUTF();
            int    regNum    = in.readInt();
            Reader reader    = new Reader(firstName, lastName, regNum);
            int    borrowed  = in.readInt();
            for (int j = 0; j < borrowed; j++) {
                reader.borrowBook(readBook(in));
            }
            readers.add(reader);
        }
    }

    // Допоміжний метод: зчитати один Book з потоку

    private static Book readBook(ObjectInputStream in) throws IOException {
        String title   = in.readUTF();
        int    year    = in.readInt();
        int    edition = in.readInt();
        Book   book    = new Book(title, year, edition);
        int    authCnt = in.readInt();
        for (int k = 0; k < authCnt; k++) {
            book.addAuthor(new Author(in.readUTF(), in.readUTF()));
        }
        return book;
    }

    //  Getters та Setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Book>   getBooks()   { return books;   }
    public void setBooks(List<Book> books) { this.books = books; }

    public List<Reader> getReaders() { return readers; }
    public void setReaders(List<Reader> readers) { this.readers = readers; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Library: ").append(name).append("\n");
        sb.append("Book collection (").append(books.size()).append("):\n");
        for (Book b : books)   sb.append("  - ").append(b).append("\n");
        sb.append("Readers (").append(readers.size()).append("):\n");
        for (Reader r : readers) sb.append("  - ").append(r).append("\n");
        return sb.toString();
    }
}
