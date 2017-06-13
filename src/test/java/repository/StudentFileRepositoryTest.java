package repository;

import domain.Student;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Moni on 5/24/2017.
 */
//@RunWith(JMockit.class)
public class StudentFileRepositoryTest {
    Validator<Student> v;
    StudentFileRepository repo;
    List<Student> students;

    @Before
    public void setUp() throws Exception {
        v = new Validator<Student>() {
            @Override
            public void validate(Student entity) throws ValidatorException {

            }
        };
        repo = new StudentFileRepository(v, "D:\\Licenta\\C8\\src\\test\\repository\\test.txt");
        students = new ArrayList<Student>();
        Student student1 = new Student("bmig0003", "Monica", "Barutia", "bm@yahoo.com");
        Student student2 = new Student("nm0020", "Nemes", "Madalina", "nm@yahoo.com");
        students.add(student1);
        students.add(student2);

    }



    @After
    public void tearDown() throws Exception {

    }

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void loadData() throws Exception {



        List<Student> studenti = new ArrayList<Student>();

            studenti = repo.loadData("D:\\Licenta\\C8_maven\\src\\test\\java\\repository\\read.txt");
            repo.loadData();

        assertEquals(students.size(),studenti.size());

    }



    @Test
    public void writeToFile() throws Exception {
        repo.writeToFile("D:\\Licenta\\C8\\src\\test\\repository\\write.txt", students);

        List<Student> studenti = new ArrayList<Student>();
        studenti = read("D:\\Licenta\\C8\\src\\test\\repository\\write.txt");
        assertEquals(students.size(),studenti.size());

        repo.writeToFile();


    }

    public List<Student> read(String file) {

        List<Student> students = new ArrayList<Student>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] atributes = line.split(";");
                Student t = new Student(atributes[0], atributes[1], atributes[2], atributes[3]);
                students.add(t);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }
}
