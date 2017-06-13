package repository;


import domain.HasID;
import domain.validators.ValidatorException;
import utils.Observable;

public interface Repository<E extends HasID<ID>, ID> {
	/**
	 * 
	 * @param id the entity with the given id, otherwise return null
	 * @return
	 */
	E findOne(ID id);
	
	/**
	 * 
	 * @return  all entities as an iterable collection
	 */
	Iterable<E> findAll();
	
	/**
	 * Saves the given entity
	 * @param entity
	 * @return null if the entity was saved, otherwise (id already exists) returns the entity. 
	 * @throws ValidatorException if entity is not valid
	 */
	E save(E entity) throws ValidatorException;
	
	/**
	 * 
	 * @param id
	 * @return null if there is no entity with the given id, otherwise the removed entity. 
	 *
	 */
	E delete(ID id);
	
	/**
	 * 
	 * @param entity
	 * @return null if the entity was updated, otherwise (id does not exist) returns the
     *         entity. 
	 * @throws ValidatorException
	 */
	E update(E entity) throws ValidatorException;
	public void loadData();

}



