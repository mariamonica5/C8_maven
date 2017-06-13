package repository;

import domain.Student;
import domain.validators.Validator;

import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by camelia on 11/21/2016.
 */
public class StudentXMLStAXRepository extends AbstractFileRepository<Student,String> {

    public StudentXMLStAXRepository(Validator<Student> v, String fileName) {
        super(v,fileName);
    }

    @Override
    public void loadData()  {
        loadData(super.fileName, super.entities);
    }

    public List<Student> loadData(String filename, List<Student> stud){

        InputStream fileInputStream = null;//ClassLoader.getSystemResourceAsStream(fileName);
        try {
            fileInputStream = new FileInputStream(filename);
            stud = readFromXML(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return stud;
    }

    public List<Student> readFromXML(InputStream is) throws XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        try {
            reader = inputFactory.createXMLStreamReader(is);
            return readDocument(reader);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public void writeToFile() {

        writeToFile(super.fileName, super.entities);

    }

    public void writeToFile(String filename, List<Student> entities){

        OutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filename);
            writeToXML(fileOutputStream, entities);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeToXML(OutputStream fileOutputStream) throws XMLStreamException{
       writeToXML(fileOutputStream, super.entities);
    }

    private void writeToXML(OutputStream fileOutputStream,List<Student> entities ) throws XMLStreamException{
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        factory.createXMLEventWriter(fileOutputStream);
        XMLStreamWriter streamWriter =
                factory.createXMLStreamWriter(fileOutputStream);
        streamWriter.writeStartElement("students");
        entities.forEach(x -> {
            try {
                writeStudent(x, streamWriter);
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });
        streamWriter.writeEndElement();
    }




    public void writeStudent(Student x, XMLStreamWriter writer) throws XMLStreamException{
        writer.writeStartElement("student");
        writer.writeAttribute("id",x.getId());

        writer.writeStartElement("firstName");
        writer.writeAttribute("value",x.getFirstName());
        writer.writeEndElement();

        writer.writeStartElement("lastName");
        writer.writeAttribute("value",x.getLastName());
        writer.writeEndElement();

        writer.writeStartElement("email");
        writer.writeAttribute("value",x.getEmail());
        writer.writeEndElement();

        writer.writeEndElement();
        writer.flush();
    }



    private List<Student> readDocument(XMLStreamReader reader) throws XMLStreamException {
        List<Student> studs=new ArrayList<>();
        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals("student"))
                    studs.add(readStudent(reader));
            }
        }
        return studs;
    }

    private Student readStudent(XMLStreamReader reader) throws XMLStreamException{
        Student Student;
        String id=reader.getAttributeValue(null, "id");
        String firstName="";
        String lastName="";
        String email="";
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    if (elementName.equals("firstName"))
                        firstName=reader.getAttributeValue(null, "value");
                    else if (elementName.equals("lastName"))
                        lastName=reader.getAttributeValue(null, "value");
                    else if (elementName.equals("email"))
                        email=reader.getAttributeValue(null, "value");
                    break;
                case XMLStreamReader.END_ELEMENT:
                    String elementName2 = reader.getLocalName();
                    if (elementName2.equals("student"))
                        return new Student(id,firstName,lastName,email);
            }
        }
        throw new XMLStreamException("Premature end of file");
    }


}
