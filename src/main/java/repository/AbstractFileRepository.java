package repository;

import domain.HasID;
import domain.Student;
import domain.validators.Validator;
import domain.validators.ValidatorException;

/**
 * Created by camelia on 11/21/2016.
 */
public abstract class AbstractFileRepository<E extends HasID<ID>,ID> extends InMemoryRepository<E,ID> {
    protected String fileName;
    public AbstractFileRepository(){}
    public AbstractFileRepository(Validator<E> v, String fileName) {
        super(v);
        this.fileName=fileName;
        //loadData();
    }

    @Override
    public E save(E e) throws ValidatorException {
        E a=super.save(e);
        writeToFile();
        return a;
    }

    @Override
    public E update(E e) throws ValidatorException {
        E a=super.update(e);
        writeToFile();
        return a;
    }

    @Override
    public E delete(ID id) {
        E a=super.delete(id);
        writeToFile();
        return a;
    }

    public abstract void loadData();
    public abstract void writeToFile();
}
