/**
 * Artigo SOA
 */
package br.com.pizzadeliverycloud.persistence.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.pizzadeliverycloud.persistence.IntercefeDao;



/**
 * DAO Generico que implementa os metodos basicos utilizando JPA.
 * 
 * @author Ronaldo R. Nascimento
 * @since 23/03/2012
 * @param <K> Key - chave da entidade
 * @param <E> Entity - entidade
 */
public abstract class AbstractDao<K, E> implements IntercefeDao<K, E> {

    /** Class da entidade. */
    private Class<E> entityClass;

    protected EntityManager entityManager;
    
    /**
     * Construtor padrão.
     * 
     * @param factory
     *            EntityManagerFactory
     * @param entityClass
     *            Class da entidade
     */
    public AbstractDao(final EntityManagerFactory factory, final Class<E> entityClass) {

    	entityManager = factory.createEntityManager();
    	
        this.entityClass = entityClass;
    }

    /**
     * Busca a entidade pelo id.
     * 
     * @param id Chave da entidade
     * @return Entidade
     */
    public E findById(final K id) {
    	
    	E entity = null;
    	
    	try {
	    	
	    	entity = entityManager.find(entityClass, id);
	    
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return entity;
    }

    /**
     * Remove a entidade.
     * 
     * @param entity
     *            Entidade
     */
    public void remove(final E entity) {
    	
    	EntityTransaction tx = entityManager.getTransaction();
    	
    	try {
    		tx.begin();
    		
    		entityManager.remove(entity);
    		
    		tx.commit();
    	} catch (Exception e) {
    		e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		}
        
    }

    /**
     * Persiste, cria a entidade.
     * 
     * @param entity
     *            Entidade
     */
    public void persist(final E entity) {
    	EntityTransaction tx = entityManager.getTransaction();
    	
    	try {
    		tx.begin();
    		
    		entityManager.persist(entity);
    		
    		tx.commit();
    	} catch (Exception e) {
    		e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} 
    }

    /**
     * Atualiza a entidade.
     * 
     * @param entity
     *            Entidade
     */
    public void update(final E entity) {
    	EntityTransaction tx = entityManager.getTransaction();
    	
    	try {
    		tx.begin();
    		
    		entityManager.merge(entity);
    		
    		
    		tx.commit();
    	} catch (Exception e) {
    		e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} 
    }

    /**
     * Atualiza a entidade.
     * 
     * @param entity
     *            Entidade
     * @return Entidade atualizada
     */
    public E merge(final E entity) {
    	EntityTransaction tx = entityManager.getTransaction();
    	
    	E result = null;
    	try {
    		tx.begin();
    		
    		result = entityManager.merge(entity);
    		
    		
    		tx.commit();
    	} catch (Exception e) {
    		e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} 
		
		return result;
    }

    /**
     * Persiste, cria a entidade.
     * 
     * @param entity
     *            Entidade
     */
    public void create(final E entity) {
        
    	EntityTransaction tx = entityManager.getTransaction();
    	
    	try {
    		tx.begin();
    		
    		entityManager.persist(entity);
    		
    		
    		tx.commit();
    	} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			
		} 
    }

    /**
     * Faz flush no banco de dados das alterações pendentes.
     */
    public void flush() {
    	EntityTransaction tx = entityManager.getTransaction();
    	
    	try {
    		tx.begin();
    		
    		entityManager.flush();
    		
    		tx.commit();
    	} catch (Exception e) {
    		e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		}
    }


    @SuppressWarnings("unchecked")
	public List<E> findAll() {
    	EntityTransaction tx = entityManager.getTransaction();
    	
    	List<E> resultList = null;
    	
    	try {
    		tx.begin();
    		
			resultList = entityManager.createQuery(
    			"select x from " + entityClass.getSimpleName() + " x").getResultList();
    		
    		tx.commit();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		
		return resultList;
    }
    
    
    /**
     * 
     * Seta os parametros na query apartir de um array de objetos ordenado.
     * 
     * @param query
     *            - Query a ser executada
     * @param params
     *            - Array de parametros a ser setados
     */
    public void setParameters(final Query query, final Object[] params) {

        if (query != null && params != null) {
            for (int i = 1; i <= params.length; i++) {
                query.setParameter(i, params[i - 1]);
            }
        }

    }
    
}
