package v3;

import java.io.*;

public class Author implements Externalizable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;

    // Обов'язковий конструктор без параметрів для Externalizable

    public Author() {}

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName  = lastName;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(firstName);
        out.writeUTF(lastName);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        firstName = in.readUTF();
        lastName  = in.readUTF();
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
