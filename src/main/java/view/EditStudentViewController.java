package view;

import domain.Student;
import domain.validators.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.StudentService;

/**
 * Created by camelia on 11/15/2016.
 */
public class EditStudentViewController {

    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldEmail;

    private StudentService service;
    Stage dialogStage;
    Student student;

    @FXML
    private void initialize() {
    }


    public void setService(StudentService service,  Stage stage, Student s) {
        this.service = service;
        this.dialogStage=stage;
        this.student=s;
        if (s!=null) {
            setFields(s);
            textFieldId.setEditable(false);
        }

    }

    @FXML
    public void handleSave(){
        String id=textFieldId.getText();
        String nume=textFieldLastName.getText();
        String pren=textFieldFirstName.getText();
        String email=textFieldEmail.getText();
        Student s=new Student(id,pren,nume,email);
        if (student==null) {
            dialogStage.close();
            saveStudent(s);
        }
        else
        {
            dialogStage.close();
            updateStudent(s);
        }

    }

    private void updateStudent(Student s) {
        try {
            Student  saved=service.update(s);
            if (saved==null) {
                MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION, "Editare cu succes", "Studentul a fost modificat!");
                clearFields();
                dialogStage.close();
            }
            else
                MessageAlert.showErrorMessage(dialogStage,"Nu Exista un student cu acest id!");
        } catch (ValidatorException e1) {
            MessageAlert.showErrorMessage(dialogStage,e1.getMessage());
        }
    }

    private void saveStudent(Student s)
    {
        try {
            Student  saved=service.save(s);
            if (saved==null) {
                MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION, "Salvare cu succes", "Studentul a fost adaugat!");
                clearFields();
            }
            else
                MessageAlert.showErrorMessage(dialogStage,"Exista deja un student cu acest id!");
        } catch (ValidatorException e1) {
            MessageAlert.showErrorMessage(dialogStage,e1.getMessage());
        }
    }

    private void clearFields() {
        textFieldFirstName.setText("");
        textFieldLastName.setText("");
        textFieldEmail.setText("");
        textFieldId.setText("");
    }
    private void setFields(Student s)
    {
        textFieldFirstName.setText(s.getFirstName());
        textFieldLastName.setText(s.getLastName());
        textFieldEmail.setText(s.getEmail());
        textFieldId.setText(s.getId());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
