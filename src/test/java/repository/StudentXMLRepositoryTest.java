package repository;

import domain.Student;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Moni on 6/2/2017.
 */
public class StudentXMLRepositoryTest {

    StudentXMLRepository repo;
    Validator<Student>v;
    File file;
    Student student1, student2;
    List<Student> students;

    @Before
    public void setUp() throws Exception {
        v = new Validator<Student>() {
            @Override
            public void validate(Student entity) throws ValidatorException {

            }
        };
        file= new File("D:\\Licenta\\C8_maven\\src\\test\\resources\\xmlfile.xml");
        repo= new StudentXMLRepository(v, "D:\\Licenta\\C8_maven\\src\\test\\resources\\xmlfile.xml");
        student1 = new Student("1", "Pop", "Vasile", "pv@yahoo.com");
        student2 = new Student("2", "Gaga", "Alex", "ga@yahoo.com");
        students= new ArrayList<Student>();
        students.add(student1);
        students.add(student2);
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void loadData() throws Exception {
        List<Student> stud = new ArrayList<Student>();
        stud=repo.loadData(file);
        repo.loadData();
        assertEquals(students.size(),stud.size());
        assertEquals(student1.getFirstName(),stud.get(0).getFirstName());
        assertEquals(student1.getLastName(),stud.get(0).getLastName());
        assertEquals(student1.getEmail(),stud.get(0).getEmail());
        assertEquals(student2.getFirstName(),stud.get(1).getFirstName());
        assertEquals(student2.getLastName(),stud.get(1).getLastName());
        assertEquals(student2.getEmail(),stud.get(1).getEmail());


    }

    @Test
    public void writeToFile() throws Exception {
        List<Student> stud = new ArrayList<Student>();
        repo.writeToFile(students);
        stud=repo.loadData(file);
        assertEquals(students.size(),stud.size());
        assertEquals(student1.getFirstName(),stud.get(0).getFirstName());
        assertEquals(student1.getLastName(),stud.get(0).getLastName());
        assertEquals(student1.getEmail(),stud.get(0).getEmail());

    }



    @Test
    public void writeToFile2() throws Exception {
        List<Student> stud = new ArrayList<Student>();
        repo.writeToFile2(students);
        stud=repo.loadData(file);
        assertEquals(students.size(),stud.size());
        assertEquals(student1.getFirstName(),stud.get(0).getFirstName());
        assertEquals(student1.getLastName(),stud.get(0).getLastName());
        assertEquals(student1.getEmail(),stud.get(0).getEmail());
    }

}