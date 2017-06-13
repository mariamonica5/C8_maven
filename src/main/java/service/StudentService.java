package service;

import java.util.*;
import java.util.concurrent.*;

import domain.Student;
import domain.validators.ValidatorException;
import javafx.concurrent.Task;
import repository.Repository;
import utils.*;
import utils.Observer;

public class StudentService implements utils.Observable<Student> {
	private Repository<Student,String> studentRepo;
	protected List <Observer<Student>> observers = new ArrayList<Observer<Student>>();

	private ExecutorService executorService = Executors.newFixedThreadPool(2);

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public ExecutorService getExecutorService() {
		return executorService;

	}

	public void shutDownExecutor(){executorService.shutdown();}

	public StudentService(Repository<Student,String> repo)
	{
		studentRepo=repo;
	}

	public Student save(Student t) throws ValidatorException
	{
		Student s = studentRepo.save(t);
		if (s==null)
		{
			notifyObservers();
		}
		return s;
	}

	public Student update(Student t) throws ValidatorException
	{
		Student s = studentRepo.update(t);
		if (s==null)
		{
			notifyObservers();
		}
		return s;
	}

	public Student delete(String id)
	{
		Student s = studentRepo.delete(id);
		if (s!=null)
		{
			notifyObservers();
		}
		return s;
	}

	public List<Student>  getAllStudents()
	{

		List<Student> s=new ArrayList<>();
		Iterable<Student> list=studentRepo.findAll();
		for (Student t:list)
			s.add(t);
		return s;
	}

	public Future<List<Student>> getAllStudentsAsync()
	{
		Callable<List<Student>> callable=()-> getAllStudents();
		Future<List<Student>> futureStuds=executorService.submit(callable);
		return futureStuds;
	}

	public void loadData()
	{
		studentRepo.loadData();
	}
	@Override
	public void addObserver(Observer<Student> o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer<Student> o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(){
		for(Observer<Student> o : observers){
			o.update(this);
		}
	}
}
