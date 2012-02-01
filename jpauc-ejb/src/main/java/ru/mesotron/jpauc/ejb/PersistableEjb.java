package ru.mesotron.jpauc.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.mesotron.jpauc.domain.Persistable;

/**
 * Session Bean implementation class PersistableManager
 */
@Stateless
@LocalBean
public class PersistableEjb<T extends Persistable> {

	@PersistenceContext
	private EntityManager em;

	protected Class<T> clazz;

    /**
     * Default constructor. 
     */
    @SuppressWarnings("unchecked")
	public PersistableEjb() {
    	if (clazz == null) {
    		Type genericSuperclass = getClass().getGenericSuperclass();
    		if (genericSuperclass instanceof ParameterizedType) {
    			clazz = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]; 
    		}
    	}
    }

    @SuppressWarnings("unchecked")
	public Class<T> getClazz() {
        if (clazz == null) {
            Type genericSuperclass = getClass().getGenericSuperclass();
            // Allow this class to be safely instantiated with or without a parameterized type
            if (genericSuperclass instanceof ParameterizedType) {
            	clazz = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            }
        }
        return clazz;
    }
    
    protected void beforePersist(T entity) {

    }

    protected void afterPersist(T entity) {

    }

    protected void beforeUpdate(T entity) {

    }

    protected void beforeCreate(T entity) {

    }

    public Long persist(T entity) {
    	entity.setVersion(entity.getVersion() + 1);
        beforePersist(entity);
        if (entity.getId() != null) {
        	beforeUpdate(entity);
            entity = em.merge(entity);
        }
        else {
        	beforeCreate(entity);
            em.persist(entity);
        }
        afterPersist(entity);
        return entity.getId();
    }
    
    public Map<Long, T> persistAll(Iterable<T> entities) {
        Map<Long, T> result = new HashMap<Long, T>();
        for (T e : entities) {
            result.put(persist(e), e);
        }
        return result;
    }

    public void remove(T entity) {
    	entity = get(entity.getId());
        if (entity != null) {
            em.remove(entity);
        }
    }
    
    public void removeAll(Iterable<T> entities) {
        for (T e : entities) {
            remove(e);
        }
    }

    public void removeById(Long id) {
    	T entity = get(id);
    	if (entity != null) {
    		em.remove(entity);
    	}
    }

    public void removeByIds(Iterable<Long> ids) {
        for (Long id : ids) {
            removeById(id);
        }
    }
    
    public T get(Long id) {
    	T result = null;
    	if (id != null && id > 0) {
    		result = em.find(clazz, id);
    	}
        return result;
    }
    
	public Long getCount() {
        CriteriaBuilder  builder  = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<T>          root     = criteria.from(clazz);
        criteria.select(builder.count(root));
        TypedQuery<Long> query = em.createQuery(criteria);

        return query.getSingleResult();
    }
    
    public List<T> listAll(int start, int count) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> root = criteria.from(clazz);
        criteria.orderBy(builder.asc(root.get("id")));
        TypedQuery<T> query = em.createQuery(criteria);
        query.setFirstResult(start);
        if (count > 0) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    public List<T> listAll() {
        return listAll(0, 0);
    }

    public T getByProperty(String property, Object value) {
        T result = null;
        try {
            CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(clazz);
            Root<T> c = criteria.from(clazz);
            Path<Object> prop = c.get(property);
            criteria.where(em.getCriteriaBuilder().equal(prop, value));
            result = em.createQuery(criteria).setMaxResults(1).getSingleResult();
        }
        catch (NoResultException e) {
            result = null;
        }
        return result;
    }
    
    public List<T> listByProperty(String property, Object value) {
        CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(clazz);
        Root<T> c = criteria.from(clazz);
        Path<Object> prop = c.get(property);
        criteria.where(em.getCriteriaBuilder().equal(prop, value));
        return em.createQuery(criteria).getResultList();
    }
}
