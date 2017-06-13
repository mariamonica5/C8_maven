package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import startApplic.Main;

/**
 * Created by camelia on 11/23/2016.
 */
public class RootLayoutController {
    Main mainApp;
    @FXML
    MenuItem menuItemFileStudents;
    @FXML
    MenuItem menuItemFileDiscipline;

    public RootLayoutController()
    {

    }

    public void setMainApplic(Main app)
    {
        this.mainApp=app;
    }

    @FXML
    public void handleStudentsCRUD()
    {
        mainApp.initStudentViewLayout();
    }

    @FXML
    public void handleDisciplineCRUD()
    {

        mainApp.initDisciplinaViewLayout();
    }

}
