package v1;

import java.io.*;

public class LibraryDriver {

    private static final String FILE_NAME = "library_v1.ser";

    // Серіалізація бібліотеки у файл

    public static void serialize(Library library) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(library);
            System.out.println("\n[VERS1] The library has been serialized into a file: " + FILE_NAME);
        }
    }

    // Десеріалізація бібліотеки з файлу

    public static Library deserialize() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Library library = (Library) ois.readObject();
            System.out.println("[VERS1] The library has been deserialized from a file: " + FILE_NAME);
            return library;
        }
    }

    public static void main(String[] args) {
        System.out.println(" VERSION 1: All classes - Serializable ");

        // Автори

        Author TestAuthor1 = new Author("Author1", "Surname1");
        Author TestAuthor2 = new Author("Author2", "Surname2");
        Author TestAuthor3 = new Author("Author3", "Surname3");
        Author TestAuthor4 = new Author("Author4", "Surname4");

        // Книги
        Book Book1 = new Book("TestBook1", 1840, 1);
        Book1.addAuthor(TestAuthor1);

        Book Book2 = new Book("TestBook2", 1911, 3);
        Book2.addAuthor(TestAuthor3);

        Book Book3 = new Book("TestBook3", 1954, 5);
        Book3.addAuthor(TestAuthor4);

        Book Book4 = new Book("TestBook4", 1883, 2);
        Book4.addAuthor(TestAuthor2);

        Library library = new Library("Central City Library");
        library.addBook(Book1);
        library.addBook(Book2);
        library.addBook(Book3);
        library.addBook(Book4);

        // Читачі

        Reader Test1   = new Reader("Test1", "Surname1", 1001);
        Reader Test2 = new Reader("Test2", "Surname2", 1002);
        Reader Test3 = new Reader("Test3", "Surname3", 1003);

        library.registerReader(Test1);
        library.registerReader(Test2);
        library.registerReader(Test3);

        // Початковий стан

        System.out.println(" Initial status ");
        System.out.println(library);

        // Видача книг

        System.out.println(" Book Checkout ");
        library.lendBook(Test1,   Book1);
        library.lendBook(Test2, Book3);
        library.lendBook(Test3, Book2);
        library.lendBook(Test1, Book3);

        // Стан після видачі

        System.out.println("\n Status after issuance ");
        System.out.println(library);

        // Серіалізація

        try {
            serialize(library);

            // Десеріалізація

            Library restored = deserialize();

            System.out.println("\n Status AFTER deserialization ");
            System.out.println(restored);

            // Повернення книги (у відновленій системі)

            System.out.println(" Returning a book ");
            Reader restoredIvan = restored.getReaders().get(0);
            Book   restoredKobzar = restoredIvan.getBorrowedBooks().get(0);
            restored.returnBook(restoredIvan, restoredKobzar);

            System.out.println("\n Final status ");
            System.out.println(restored);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Serialization error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
