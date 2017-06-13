package service;

import domain.Student;
import repository.Repository;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Moni on 5/15/2017.
 */
public class StudentServiceTest {

    private Student student=null;
    private Student student2=null;
    private Student student3=null;
    private StudentService studentService=null;
    private List<Student> s=null;

    @org.junit.Before
    public void setUp() throws Exception {
        student= new Student("nmig0018","Madalina", "Nemes", "nm@yahoo.com");
        student2=new Student("bmig0003", "Monica","Barutia","bm@yahoo.com");
        student3=new Student("gjig0012","Judith","Gozner", "gj@yahoo.com");
        s=new ArrayList<>();
        s.add(student);
        s.add(student2);
        s.add(student3);

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void shutDownExecutor() throws Exception {
        studentService = new StudentService(null);
        ExecutorService executorService = mock(ExecutorService.class);
        studentService.setExecutorService(executorService);

        studentService.shutDownExecutor();

         executorService = studentService.getExecutorService();
        verify(executorService).shutdown();
    }

    @org.junit.Test
    public void saveWithNotNull() throws Exception {
        Repository repository = mock(Repository.class);
        when(repository.save(any(Student.class))).thenReturn(student);

        Observer observer = mock(Observer.class);

        studentService = new StudentService(repository);
        studentService.addObserver(observer);

        Student result = studentService.save(student);

        assertEquals(student, result);
       // assertEquals(null, result);

        verifyZeroInteractions(observer);


    }

    @org.junit.Test
    public void saveWithNull() throws Exception {
        Repository repository = mock(Repository.class);
//        when(repository.save(any(Student.class))).thenReturn(null);

        Observer observer = mock(Observer.class);

        studentService = new StudentService(repository);
        studentService.addObserver(observer);

        Student result = studentService.save(student);

        assertNull(result);

        verify(observer).update(studentService);


    }

    @org.junit.Test
    public void updateWithNull() throws Exception {

        Repository repository= mock(Repository.class);
        Observer observer=mock(Observer.class);

        studentService=new StudentService(repository);
        studentService.addObserver(observer);

        Student updated= studentService.update(student);

        assertEquals(null, updated);

        verify(observer).update(studentService);

    }

    @org.junit.Test
    public void updateWithoutNull() throws Exception{

        Repository repository = mock(Repository.class);
        when(repository.update(any(Student.class))).thenReturn(student);

        Observer observer=mock(Observer.class);

        studentService=new StudentService(repository);
        studentService.addObserver(observer);

        Student updated = studentService.update(student);

        assertEquals(updated, student);

        verify(observer, never()).update(studentService);


    }



    @org.junit.Test
    public void deleteWithNull() throws Exception {

        Repository repository= mock(Repository.class);
        Observer observer=mock(Observer.class);

        studentService=new StudentService(repository);
        studentService.addObserver(observer);

        Student deleted= studentService.delete(anyString());

        assertEquals(null, deleted);

        verify(observer, never()).update(studentService);

    }

    @org.junit.Test
    public void deleteWithoutNull() throws Exception{

        Repository repository=mock(Repository.class);
        when(repository.delete(any(Student.class))).thenReturn(student);

        Observer observer=mock(Observer.class);

        studentService=new StudentService(repository);
        studentService.addObserver(observer);

        Student deleted = studentService.delete(student.getId());

        assertEquals(deleted, student);

        verify(observer).update(studentService);



    }

    @org.junit.Test
    public void getAllStudents() throws Exception {

        Repository repository=mock(Repository.class);
        when(repository.findAll()).thenReturn(s);

        studentService= new StudentService(repository);

        List<Student> all=studentService.getAllStudents();

        assertEquals(all, s);


    }

    @org.junit.Test
    public void getAllStudentsAsync() throws Exception {
        Repository repository=mock(Repository.class);
        when(repository.findAll()).thenReturn(s);

        studentService= new StudentService(repository);

        Future<List<Student>> allAsync = studentService.getAllStudentsAsync();
        List<Student> allSync = allAsync.get();

        assertEquals(allSync, s);


    }

    @org.junit.Test
    public void loadData() throws Exception {

    }

    @org.junit.Test
    public void addObserver() throws Exception {

    }

    @org.junit.Test
    public void removeObserver() throws Exception {

    }

    @org.junit.Test
    public void notifyObservers() throws Exception {

    }

}