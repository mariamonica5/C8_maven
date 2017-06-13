package domain.validator;

import domain.Student;
import domain.validators.StudentValidator;
import domain.validators.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Moni on 5/15/2017.
 */

public class StudentValidatorTest {

    private Student student=null;

    @Before
    public void setUp() throws Exception {
        student=new Student("bmig0003","Monica","Barutia","bm@yahoo.com");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void validateId() throws Exception {

        thrown.expect(ValidatorException.class);
        thrown.expectMessage("Id error ");

        Student student = new Student(null,"Monica","Barutia","mb@yahoo.com");

        StudentValidator studentValidator= new StudentValidator();
        studentValidator.validate(student);




    }

    @Test
    public void validateFirstname() throws Exception {

        thrown.expect(ValidatorException.class);
        thrown.expectMessage("first name error ");

        Student student = new Student("bmig0003","","Barutia","mb@yahoo.com");

        StudentValidator studentValidator= new StudentValidator();
        studentValidator.validate(student);

    }

    @Test
    public void validateLastName() throws Exception {


        thrown.expect(ValidatorException.class);
        thrown.expectMessage("last name error ");

        Student student = new Student("bmig0003","Monica","","mb@yahoo.com");

        StudentValidator studentValidator= new StudentValidator();
        studentValidator.validate(student);


    }

    @Test
    public void validateEmail() throws Exception {

        thrown.expect(ValidatorException.class);
        thrown.expectMessage("email error error ");

        Student student = new Student("bmig0003","Monica","Barutia","");

        StudentValidator studentValidator= new StudentValidator();
        studentValidator.validate(student);


    }

}