package repository;

import domain.Student;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Moni on 6/10/2017.
 */
public class StudenXMLStAX2RepositoryTest {

    StudenXMLStAX2Repository repo, repo1;
    Validator<Student> v;
    List<Student> students;
    Student student1;
    Student student2;

    @Before
    public void setUp() throws Exception {

        v = new Validator<Student>() {
            @Override
            public void validate(Student entity) throws ValidatorException {

            }
        };


        repo=new StudenXMLStAX2Repository();
        repo1= new StudenXMLStAX2Repository(v, "./src/test/resources/StudentStAX2.xml");
        students = new ArrayList<Student>();
        student1 = new Student("1", "Pop", "Vasile", "pv@yahoo.com");
        student2 = new Student("2", "Gaga", "Alex", "ga@yahoo.com");
        students.add(student1);
        students.add(student2);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void loadData() throws Exception {
        List<Student> stud= new ArrayList<Student>();
        stud=repo.loadData(stud);
        repo1.loadData();
        assertEquals(students.size(),stud.size());
        assertEquals("pv@yahoo.com", stud.get(0).getEmail());
        assertEquals("Pop", stud.get(0).getFirstName());
        assertEquals("Vasile",stud.get(0).getLastName());

    }

    @Test
    public void writeToFile() throws Exception {
        repo.writeToFile(students);
        List<Student> stud= new ArrayList<Student>();
        stud=repo.loadData(stud);
        assertEquals(students.size(),stud.size());
        assertEquals("pv@yahoo.com", stud.get(0).getEmail());
        assertEquals("Pop", stud.get(0).getFirstName());
        assertEquals("Vasile",stud.get(0).getLastName());


    }

}