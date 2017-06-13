package repository;

import java.util.ArrayList;
import java.util.List;

import domain.HasID;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import utils.Observer;

public abstract class InMemoryRepository<E extends HasID<ID>, ID> implements Repository<E, ID>{
	
	protected List<E> entities=new ArrayList<>();
	protected Validator<E> validator;
	public InMemoryRepository(){}
	public InMemoryRepository(Validator<E> v) {
		validator=v;
	}

	@Override
	public E findOne(ID id) {
		if (null==id)
			return null;
		for(E e:entities)
			if (e.getId().equals(id))
				return e;
		return null;

	}

	@Override
	public Iterable<E> findAll() {
		if (entities.size()==0)
			loadData();
		return entities;
	}

	@Override
	public E save(E entity) throws ValidatorException {
		for (E e:entities)
			if (e.getId().equals(entity.getId()))
				return entity;
		validator.validate(entity);
		entities.add(entity);
		return null;
	}

	@Override
	public E delete(ID id) {
		for (int i=0; i<=entities.size(); i++)
		{
			E entity=entities.get(i);
			if (entity.equals(id))
				entities.remove(i);
				return entity;
		}
		return null;
	}

	@Override
	public E update(E entity) throws ValidatorException {
		validator.validate(entity);
		for (int i=0; i<entities.size(); i++)
		{
			if (entities.get(i).getId().equals(entity.getId()))
			{
				entities.set(i, entity);
				return null;
			}
		}
		return entity;
	}

	public abstract void loadData();

}
