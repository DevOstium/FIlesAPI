package com.filesAPI.daos.outroexemplo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Blob;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDAOExemplo<T> {

	@Autowired
    private SessionFactory sessionFactory;
 
    private Class<T> type;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDAOExemplo() {
    	super();
    	
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class)pt.getActualTypeArguments()[0];
    }

    protected Long max(String propertyName){
    	Criteria criteria = this.getSession().createCriteria(type);
    	criteria.setProjection(Projections.max(propertyName));
    	Object result = criteria.uniqueResult();
    	return (result == null ? 0l : (Long)result);
    }
    
    public Long count(Map<String, Object> params) {
        
    	Criteria criteria = this.getSession().createCriteria(type);
    	criteria.setProjection(Projections.rowCount());
 
        if (params != null && !params.isEmpty()) {
        	for (Entry<String, Object> entry : params.entrySet())
	    	   criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
        
        return (Long) criteria.uniqueResult();
    }
    
	public boolean exists(Map<String, Object> params) {
		return (this.count(params).longValue() > 0 ? true : false);
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Serializable id) {
		return (T)getSession().get(type, id);
	}
    
	@SuppressWarnings("unchecked")
	public List<T> listBy(Map<String, Object> params) {
		
		Criteria criteria = this.getSession().createCriteria(type);
        if (params != null && !params.isEmpty()) {
        	for (Entry<String, Object> entry : params.entrySet())
	    	   criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}

		return (List<T>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listAll() {
		return (List<T>)this.getSession().createCriteria(type).list();
	}
	
	public Serializable insert(T entity) {
		return getSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	public T update(T entity) {
		return (T)getSession().merge(entity);
	}

	public void save(T entity) {
		this.getSession().saveOrUpdate(entity);
	}
	
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	//
    protected Session getSession() {
    	return this.sessionFactory.getCurrentSession();
    }
    
    //
	public Blob createBlob(byte[] bytes) {
		return Hibernate.getLobCreator(this.getSession()).createBlob(bytes);
	}
}