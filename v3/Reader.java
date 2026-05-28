package v3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader implements Externalizable {
    private static final long serialVersionUID = 1L;

    private String     firstName;
    private String     lastName;
    private int        registrationNumber;
    private List<Book> borrowedBooks;

    public Reader() {
        this.borrowedBooks = new ArrayList<>();
    }

    public Reader(String firstName, String lastName, int registrationNumber) {
        this.firstName          = firstName;
        this.lastName           = lastName;
        this.registrationNumber = registrationNumber;
        this.borrowedBooks      = new ArrayList<>();
    }

    public void borrowBook(Book book) { borrowedBooks.add(book); }

    public boolean returnBook(Book book) { return borrowedBooks.remove(book); }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(firstName);
        out.writeUTF(lastName);
        out.writeInt(registrationNumber);
        out.writeInt(borrowedBooks.size());
        for (Book b : borrowedBooks) {
            b.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        firstName          = in.readUTF();
        lastName           = in.readUTF();
        registrationNumber = in.readInt();
        int count = in.readInt();
        borrowedBooks = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Book b = new Book();
            b.readExternal(in);
            borrowedBooks.add(b);
        }
    }

    public String getFirstName()          { return firstName; }
    public void   setFirstName(String s)  { this.firstName = s; }

    public String getLastName()           { return lastName; }
    public void   setLastName(String s)   { this.lastName = s; }

    public int  getRegistrationNumber()   { return registrationNumber; }
    public void setRegistrationNumber(int n){ this.registrationNumber = n; }

    public List<Book> getBorrowedBooks()  { return borrowedBooks; }
    public void setBorrowedBooks(List<Book> b){ this.borrowedBooks = b; }

    @Override
    public String toString() {
        return "[#" + registrationNumber + "] " + firstName + " " + lastName +
               " | books on hand: " + borrowedBooks;
    }
}
