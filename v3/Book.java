package v3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Book implements Externalizable {
    private static final long serialVersionUID = 1L;

    private String       title;
    private List<Author> authors;
    private int          year;
    private int          edition;

    public Book() {
        this.authors = new ArrayList<>();
    }

    public Book(String title, int year, int edition) {
        this.title   = title;
        this.year    = year;
        this.edition = edition;
        this.authors = new ArrayList<>();
    }

    public void addAuthor(Author author) { authors.add(author); }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(title);
        out.writeInt(year);
        out.writeInt(edition);
        out.writeInt(authors.size());
        for (Author a : authors) {
            a.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title   = in.readUTF();
        year    = in.readInt();
        edition = in.readInt();
        int count = in.readInt();
        authors = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Author a = new Author();
            a.readExternal(in);
            authors.add(a);
        }
    }

    public String getTitle()        { return title;   }
    public void   setTitle(String t){ this.title = t; }

    public List<Author> getAuthors()            { return authors; }
    public void         setAuthors(List<Author> a){ this.authors = a; }

    public int  getYear()           { return year;    }
    public void setYear(int y)      { this.year = y;  }

    public int  getEdition()        { return edition; }
    public void setEdition(int e)   { this.edition = e; }

    @Override
    public String toString() {
        return "\"" + title + "\" (authors: " + authors + ", year: " + year + ", ed. " + edition + ")";
    }
}
