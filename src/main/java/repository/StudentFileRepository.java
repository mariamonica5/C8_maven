package repository;

import domain.Student;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StudentFileRepository extends AbstractFileRepository<Student,String> {
	public StudentFileRepository(Validator<Student> v, String fName) {
		super(v,fName);
	}

	public void loadData()
	{
		loadData(super.fileName);
	}

	public List<Student> loadData(String filename){
		List<Student> students = new ArrayList<Student>();

		try(BufferedReader br=new BufferedReader(new FileReader(filename))){
			String line;
			while((line=br.readLine())!=null){
				String[] atributes=line.split(";");
				if(atributes.length!=4)
					throw new Exception("Linia nu este valida!");
				Student t=new Student(atributes[0],atributes[1],atributes[2],atributes[3]);
				students.add(t);
				super.save(t);


			}
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return students;

	}

	public synchronized void writeToFile() {
		writeToFile(super.fileName, super.entities);
	}

	public synchronized  void writeToFile(String filename,  List<Student> entities){
		try (BufferedWriter br = new BufferedWriter(new FileWriter(filename))){
			entities.forEach(x -> {
				try {
					br.write(x.studentFileLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}