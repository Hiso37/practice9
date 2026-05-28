package v2;

import java.util.ArrayList;
import java.util.List;

// Клас НЕ серіалізований
public class Reader {

    private String firstName;
    private String lastName;
    private int registrationNumber;
    private List<Book> borrowedBooks;

    public Reader() {
        this.borrowedBooks = new ArrayList<>();
    }

    public Reader(String firstName, String lastName, int registrationNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationNumber = registrationNumber;
        this.borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public boolean returnBook(Book book) {
        return borrowedBooks.remove(book);
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(int n) { this.registrationNumber = n; }

    public List<Book> getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(List<Book> books) { this.borrowedBooks = books; }

    @Override
    public String toString() {
        return "[#" + registrationNumber + "] " + firstName + " " + lastName +
               " | books on hand: " + borrowedBooks;
    }
}
