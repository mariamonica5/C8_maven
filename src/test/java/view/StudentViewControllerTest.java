package view;

import domain.Student;
import domain.validators.StudentValidator;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import repository.StudentFileRepository;
import service.StudentService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javafx.geometry.VerticalDirection.DOWN;
import static org.junit.Assert.assertEquals;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

/**
 * Created by Moni on 6/1/2017.
 */
public class StudentViewControllerTest extends GuiTest {

    Validator<Student>v;
    StudentFileRepository repo;
    File file;

    @Override
    protected Parent getRootNode() {
        Parent parent = null;
        try {
            //parent = FXMLLoader.load(getClass().getResource("../../view/StudentView.fxml"));
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("/fxml/StudentView.fxml"));
            parent = loader.load();


            StudentViewController viewCtrl=loader.getController();
            Validator<Student> vali= new StudentValidator();
            StudentFileRepository studentRepo=new StudentFileRepository(vali,"./src/test/resources/Students.txt");
            StudentService studentService =new StudentService(studentRepo);
            viewCtrl.setService(studentService);

            return parent;
        } catch (IOException ex) {
            // TODO ...
        }
        return parent;
    }

    @Before
    public void setUp() throws Exception {
        v = new Validator<Student>() {
            @Override
            public void validate(Student entity) throws ValidatorException {

            }
        };
        file= new File("./src/test/resources/Students.txt");
        repo= new StudentFileRepository(v, "./src/test/resources/Students.txt");

    }



    @Test
    public void handleSaveStudent() throws Exception {

        //verify if table exists
        Node node0= find("#studentTable");
        Assert.assertTrue(node0.isVisible());

        //verify if buttons exist

        Node nodeD= find("Delete");
        Assert.assertTrue(nodeD.isVisible());
        Node nodeS= find("#buttonSave");
        Assert.assertTrue(nodeS.isVisible());
        Node nodeU= find("#buttonUpdate");
        Assert.assertTrue(nodeU.isVisible());

        //add already existing student
        click("#buttonSave");

        sleep(2, SECONDS);

        //check if edit window appears
        Node node= find("#textFieldId");
        Assert.assertTrue(node.isVisible());
        Node node1= find("#textFieldFirstName");
        Assert.assertTrue(node1.isVisible());
        Node node2= find("#textFieldLastName");
        Assert.assertTrue(node2.isVisible());
        Node node3= find("#textFieldEmail");
        Assert.assertTrue(node3.isVisible());
        Node node4= find("Save");
        Assert.assertTrue(node4.isVisible());
        Node node5= find("Cancel");
        Assert.assertTrue(node5.isVisible());

        click("#textFieldFirstName").type("Monica");
        click("#textFieldLastName").type("Barutia");
        click("#textFieldId").type("bmig0003");
        click("#textFieldEmail").type("bm.yahoo.com");

        verifyThat("#textFieldId", hasText("bmig0003"));
        verifyThat("#textFieldFirstName", hasText("Monica"));
        verifyThat("#textFieldLastName", hasText("Barutia"));
        verifyThat("#textFieldEmail", hasText("bm.yahoo.com"));
//
        sleep(2, SECONDS);

        List<Student> before=new ArrayList<Student>();
        before=repo.loadData("./src/test/resources/Students.txt");

        click("Save");

        List<Student> after= new ArrayList<Student>();
        after=repo.loadData("./src/test/resources/Students.txt");
        assertEquals(before.size(), after.size());

        //check if message window appears

        Node node6= find("OK");
        Assert.assertTrue(node6.isVisible());
        sleep(2, SECONDS);

        click("OK");
        sleep(2, SECONDS);

        //add new student

        click("#buttonSave");

        click("#textFieldFirstName").type("Alexandru");
        click("#textFieldLastName").type("Barle");
        click("#textFieldId").type("bair1232");
        click("#textFieldEmail").type("ba.yahoo.com");

        verifyThat("#textFieldId", hasText("bair1232"));
        verifyThat("#textFieldFirstName", hasText("Alexandru"));
        verifyThat("#textFieldLastName", hasText("Barle"));
        verifyThat("#textFieldEmail", hasText("ba.yahoo.com"));
//
        sleep(2, SECONDS);

        before=repo.loadData("./src/test/resources/Students.txt");

        click("Save");
        sleep(2, SECONDS);

        after=repo.loadData("./src/test/resources/Students.txt");
        assertEquals(before.size()+1, after.size());
        Student student=repo.findOne("bair1232");
        assert student!=null;

        //check if message window appears
        Node node7= find("OK");
        Assert.assertTrue(node7.isVisible());
        click("OK");

        //cancel save

        click("#buttonSave");
        click("#textFieldFirstName").type("Catalin");
        click("#textFieldLastName").type("Cosma");
        click("Cancel");
        sleep(2, SECONDS);
    }



    @Test
    public void handleUpdateStudent() throws Exception {
        //no student to update
        sleep(1, SECONDS);
        click("#buttonUpdate");
        //verify if message dialog appears
        Node node0= find("OK");
        Assert.assertTrue(node0.isVisible());
        sleep(2, SECONDS);
        click("OK");
        sleep(2, SECONDS);

        //update student
        click("Daniel");
        sleep(1, SECONDS);
        scroll(3, DOWN);
        sleep(2, SECONDS);
        click("sds");
        click("#buttonUpdate");

        //verify if edit student window appears
        Node node= find("#textFieldId");
        Assert.assertTrue(node.isVisible());
        Node node1= find("#textFieldFirstName");
        Assert.assertTrue(node1.isVisible());
        Node node2= find("#textFieldLastName");
        Assert.assertTrue(node2.isVisible());
        Node node3= find("#textFieldEmail");
        Assert.assertTrue(node3.isVisible());
        Node node4= find("Save");
        Assert.assertTrue(node4.isVisible());
        Node node5= find("Cancel");
        Assert.assertTrue(node5.isVisible());

        click("#textFieldEmail").push(KeyCode.BACK_SPACE).push(KeyCode.BACK_SPACE).type("daniel.yahoo.com");
        click("#textFieldId");
        verifyThat("#textFieldEmail", hasText("daniel.yahoo.com"));
        sleep(2, SECONDS);
        click("Save");
        sleep(2, SECONDS);

        List<Student> studenti=new ArrayList<Student>();
        studenti=repo.loadData("./src/test/resources/Students.txt");
        assertEquals("daniel.yahoo.com",studenti.get(9).getEmail());

        //verify if message dialog appears
        node0= find("OK");
        Assert.assertTrue(node0.isVisible());

        click("OK");
        sleep(2, SECONDS);


        //cancel update
        click("Andrei");
        click("#buttonUpdate");
        click("#textFieldLastName").type("blabla");
        sleep(2, SECONDS);
        click("Cancel");

        assertEquals("Dan", studenti.get(6).getLastName());
        sleep(2, SECONDS);




    }

    @Test
    public void Delete() throws Exception {

        sleep(6, SECONDS);
        click("Andrei1");
        sleep(2, SECONDS);

        List<Student > before= repo.loadData("./src/test/resources/Students.txt");

        click("Delete");

        List<Student > after= repo.loadData("./src/test/resources/Students.txt");

        //assertTrue(before.size()==after.size()-1);
        Student student=repo.findOne("7");
        //assertTrue(null==student);
        sleep(2, SECONDS);





    }


}