package repository;

import domain.Student;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by camelia on 11/21/2016.
 */
public class StudentXMLRepository extends AbstractFileRepository<Student,String> {
    private String fileName;
    public StudentXMLRepository(Validator<Student> v, String fileName) {
        super(v,fileName);
    }

    @Override
    public void loadData()  {
        File file= new File(super.fileName);
        loadData(file);
    }

    public List<Student> loadData(File filename){

        Document document =loadDocument(filename);
        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType()==Node.ELEMENT_NODE) {   //node instanceof Element
                Element element = (Element) node;
                Student stud = createStudent(element);
                students.add(stud);
            }
        }
        super.entities.addAll(students);
        return students;
    }

    public void writeToFile() {
        writeToFile(super.entities);
    }

    public void writeToFile(List<Student> studenti){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("students");
            doc.appendChild(rootElement);
            for (Student x:studenti)
            {
                Element student = doc.createElement("student");
                rootElement.appendChild(student);
                student.setAttribute("id", x.getId());
                appendStudentElement(doc,"firstName",x.getFirstName(),student);
                appendStudentElement(doc,"lastName",x.getLastName(),student);
                appendStudentElement(doc,"email",x.getEmail(),student);
            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }


    private static void appendStudentElement(Document doc, String tagName, String textNode, Element studentNode)
    {
        Element element=doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textNode));
        studentNode.appendChild(element);
    }
    private Student createStudent(Element element) {
        String id=element.getAttributeNode("id").getValue();
        String firstName=element.getElementsByTagName("firstName").item(0).getTextContent();
        String lastName=element.getElementsByTagName("lastName").item(0).getTextContent();
        String email=element.getElementsByTagName("email").item(0).getTextContent();
        return new Student(id,firstName,lastName,email);
    }

    Document loadDocument() {
        File file= new File(super.fileName);
        loadDocument(file);
        return null;
    }

    Document loadDocument(File filename){
        try {
            //File inputFile = new File(super.fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            Document doc=null;
            docBuilder = docFactory.newDocumentBuilder();
            doc= docBuilder.parse(new FileInputStream(filename));
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void saveDocument(Document doc) {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(super.fileName));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("File saved!");
    }


    public void writeToFile2() {
        writeToFile2(super.entities);
    }

    public void writeToFile2(List<Student> studenti){

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("students");
            doc.appendChild(rootElement);
            for (Student x:studenti)
            {
                // create student element
                Element student = doc.createElement("student");
                //add it to the root
                rootElement.appendChild(student);

                // set attribute to student element
                Attr attr = doc.createAttribute("id");
                attr.setValue(x.getId());
                student.setAttributeNode(attr);

                // shorten way
                // students.setAttribute("id", "1");

                // firstname elements
                appendStudentElement(doc,"firstName",x.getFirstName(),student);
//                Element firstname = doc.createElement("firstName");
//                firstname.appendChild(doc.createTextNode(x.getFirstName()));
//                student.appendChild(firstname);

                // lastname elements
                appendStudentElement(doc,"lastName",x.getLastName(),student);
//                Element lastname = doc.createElement("lastName");
//                lastname.appendChild(doc.createTextNode(x.getLastName()));
//                student.appendChild(lastname);

                // email elements
                appendStudentElement(doc,"email",x.getEmail(),student);
//                Element email = doc.createElement("email");
//                email.appendChild(doc.createTextNode(x.getEmail()));
//                student.appendChild(email);
            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

    }

}
