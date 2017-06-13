package view;

import domain.Student;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.StudentService;
import utils.Observable;
import utils.Observer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;


public class StudentViewController implements Observer<Student>{
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;


    StudentService service;
    ObservableList<Student> model;

    ExecutorService ex = Executors.newFixedThreadPool(2);

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public StudentViewController() {

    }


    public void setService(StudentService service) {
        this.service=service;
        initModel();
        bindDataAsync2();
        //bindDataSyncroniously();
    }

    public void bindDataSyncroniously(){
        model.setAll(service.getAllStudents());
    }

    public Task<Void> loadDataTask()
    {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<Student> studs=service.getAllStudents();
                Platform.runLater(()->{
                    model.setAll(studs);
                });
                //Platform.runLater: If you need to update a GUI component
                // from a non-GUI thread, you can use that to put your update in a queue and
                // it will be handle by the GUI thread as soon as possible.
                return null;
            }
        };
    }

    public void bindDataAsync1(){
        ex.submit(loadDataTask());
        ex.shutdown();
    }

    public void bindDataAsync2()
    {
        ex.submit(()->{
            Future<List<Student>> future=service.getAllStudentsAsync();
            try {
                List<Student> studs=future.get();
                Platform.runLater(()->{
                    model.setAll(studs);
//                    model = FXCollections.observableArrayList(studs);
//                    studentTable.setItems(model);
                });
                //Platform.runLater: If you need to update a GUI component
                // from a non-GUI thread, you can use that to put your update in a queue and
                // it will be handle by the GUI thread as soon as possible.

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        });

        ex.shutdown();
    }

    public void initModel()
    {
        this.model=FXCollections.observableArrayList();
        studentTable.setItems(model);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        // Initialize the student table with the three columns.
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));

    }

    @FXML
    public void handleSaveStudent()
    {
        showStudentEditDialog(null);
    }

    @FXML
    public void handleUpdateStudent()
    {
        Student s=studentTable.getSelectionModel().getSelectedItem();
        if (s!=null) {
            showStudentEditDialog(s);
        }
        else
        {
            MessageAlert.showErrorMessage(null,"Nu ati selectat nici un student!");
        }
    }

    public void showStudentEditDialog(Student student) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentViewController.class.getResource("/fxml/EditStudentView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Student");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditStudentViewController editStudentViewController = loader.getController();
            editStudentViewController.setService(service, dialogStage,student);


            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable<Student> observable) {
        StudentService service=(StudentService)observable;
        model.setAll(service.getAllStudents());

    }
}
