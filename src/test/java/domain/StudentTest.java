package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Moni on 5/15/2017.
 */
public class StudentTest {

    Student student=null;
    Student student2=null;
    Student student3=null;

    @Before
    public void setUp() throws Exception {

        student=new Student("bmig0003","Monica","Barutia","bm@yahoo.com");
        student2= new Student("nmig0020", "Madalina", "Nemes", "nm@yahoo.com");
        student3= new Student();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setFirstName() throws Exception {

        student.setFirstName("Maria");
        assertEquals("Maria", student.getFirstName());

    }

    @Test
    public void setLastName() throws Exception {

        student.setLastName("Popescu");
        assertEquals("Popescu", student.getLastName());

    }

    @Test
    public void setEmail() throws Exception {

        student.setEmail("bmig@yahoo.com");
        assertEquals("bmig@yahoo.com", student.getEmail());

    }

    @Test
    public void getFirstName() throws Exception {

        assertEquals("Monica",student.getFirstName());
        assertEquals("Madalina",student2.getFirstName());
        assertEquals("", student3.getFirstName());

    }

    @Test
    public void getLastName() throws Exception {

        assertEquals("Barutia",  student.getLastName());
        assertEquals("Nemes", student2.getLastName());
        assertEquals("", student3.getLastName());
    }

    @Test
    public void getEmail() throws Exception {

        assertEquals("bm@yahoo.com", student.getEmail());
        assertEquals("nm@yahoo.com", student2.getEmail());
        assertEquals("", student3.getEmail());

    }

    @Test
    public void getEmailProperty() throws Exception {

    }


    @Test
    public void studentFileLine() throws Exception {

    }

    @Test
    public void getId() throws Exception {

        assertEquals("bmig0003", student.getId());
        assertEquals("nmig0020", student2.getId());
        assertEquals("", student3.getId());

    }

    @Test
    public void setId() throws Exception {

        student.setId("bmig03");
        assertEquals("bmig03", student.getId());

    }

}