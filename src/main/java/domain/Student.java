package  domain;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student implements HasID<String>{
    String id;
    String firstName;
    String lastName;
    String email;

    public Student(String id, String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id=id;
    }

    public Student() {
        this("","","","");
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public StringProperty getEmailProperty(){return new SimpleStringProperty(email);}

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String studentFileLine()
    {
        return id+";"+firstName+";"+lastName+";"+email+"\n";
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String s) {
        id=s;
    }
}
