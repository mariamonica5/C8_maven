package startApplic;

import domain.Student;
import domain.validators.StudentValidator;
import domain.validators.Validator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import repository.StudentFileRepository;
import service.StudentService;
import view.DisciplinaViewController;
import view.RootLayoutController;
import view.StudentViewController;

import java.io.IOException;

public class Main extends Application {

    BorderPane rootLayout;
    AnchorPane centerLayout;
    Stage primaryStage;
    //Repository<Student,String> studentRepo;
    StudentFileRepository studentRepo;
    StudentService studentService;

//    public void createStudentRepo(String repoName)
//    {
//        try {
//            Class repoClass=Class.forName(repoName);
//            //Constructor[] ct=repoClass.getDeclaredConstructors();
//            Constructor constr=repoClass.getDeclaredConstructor(domain.validators.Validator.class, String.class);
//            studentRepo= (Repository<Student,String> )constr.newInstance(new StudentValidator(), "./src/data/StudentStAX2.xml");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public void appContext(){
//        InputStream fileInputStream = ClassLoader.getSystemResourceAsStream("configurations.xml");
//        XMLInputFactory factory= XMLInputFactory.newFactory();
//        try {
//            XMLStreamReader reader=factory.createXMLStreamReader(fileInputStream);
//            while (reader.hasNext())
//            {
//                int eventType=reader.next();
//                switch (eventType){
//                    case XMLStreamReader.START_ELEMENT:
//                        if (reader.getLocalName().equals("bean"))
//                        {
//                            String repoName=reader.getAttributeValue(null,"class");
//                            createStudentRepo(repoName);
//                        }
//                        break;
//                }
//            }
//        } catch (XMLStreamException e) {
//            e.printStackTrace();
//        }
//
//    }

    void initRepo()
    {
        Validator<Student> vali= new StudentValidator();
        //studentRepo=new StudentFileRepository(vali,"./src/data/Students.txt");
        studentRepo=new StudentFileRepository(vali,"./src/main/resources/Students.txt");
    }

    @Override
    public void start(Stage primaryStage) {
        //new Thread(this::initRepo).start();
        initRepo();

        //studentRepo=new StudentXMLStAXRepository(vali,"./src/data/StudSAX2.xml");
        //studentRepo=new StudentXMLRepository(vali,"./src/data/Students.xml");

        //studentRepo=new StudenXMLStAX2Repository(vali,"./src/data/StudentStAX2.xml");
        //studentRepo=new StudenXMLStAX2Repository(vali,"resources/StudentStAX2.xml");

        //appContext();


        studentService=new StudentService(studentRepo);

        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Student Management System");
        primaryStage.setOnCloseRequest(e ->{
            studentService.shutDownExecutor();
            Platform.exit();
        });

        initRootLayout();
        initStudentViewLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            //Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootLayoutController rootController=loader.getController();
            rootController.setMainApplic(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void initStudentViewLayout() {
        try {
            // Load student view.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/StudentView.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            //set the service and the model for controller class
            StudentViewController viewCtrl=loader.getController();
            viewCtrl.setService(studentService);
            studentService.addObserver(viewCtrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initDisciplinaViewLayout() {
        try {
            // Load student view.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/DisciplinaView.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            //set the service and the model for controller class
            DisciplinaViewController disciplinaViewCtrl=loader.getController();
//            disciplinaViewCtrl.setService(disciplinaService);
//            disciplinaService.addObserver(disciplinaViewCtrl);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        System.out.println("ok");
        launch(args);
    }
}



//    private Field getFieldRec(Class<?> clazz, String fieldName) throws NoSuchFieldException {
//        try {
//            return clazz.getDeclaredField(fieldName);
//        } catch (NoSuchFieldException e) {
//            if (clazz.getSuperclass() == null) {
//                throw e;
//            } else {
//                return getFieldRec(clazz.getSuperclass(), fieldName);
//            }
//        }
//
//    }
//
//    public void createStudentRepo(String repoName)
//    {
//        try {
//            studentRepo=(Repository<Student,String>)Class.forName(repoName).newInstance();
//            Field f=getFieldRec(studentRepo.getClass(),"validator");
//            f.setAccessible(true);
//            f.set(studentRepo,new StudentValidator());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//    }
