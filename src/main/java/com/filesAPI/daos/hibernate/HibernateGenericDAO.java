package com.filesAPI.daos.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.filesAPI.daos.dao.GenericDAO;


public class HibernateGenericDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

	@Autowired
	private EntityManager manager;
	
	private Class<T> classeEntidade;
	
	@SuppressWarnings("unchecked")
	public HibernateGenericDAO() {
		this.classeEntidade = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public T buscarPeloCodigo(ID id) {
		return manager.find(classeEntidade, id);
	}

	@Override
	public void salvar(T entidade) {
		manager.merge(entidade);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> filtrar(T entidade, String... propriedades) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(classeEntidade);
		
		if (propriedades != null) {
			for (String propriedade : propriedades) {
				try {
					
					//Para trabalhar com reflect
					Object valor = PropertyUtils.getProperty(entidade, propriedade);
					if (valor != null) {
						if (valor instanceof String) {
							criteria.add(Restrictions.ilike(propriedade, (String) valor, MatchMode.ANYWHERE));
						} else {
							criteria.add(Restrictions.eq(propriedade, valor));
						}
					}
				} catch (Exception e) {
					throw new RuntimeException("Propriedade não encontrada", e);
				}
			}
		}
		
		return criteria.list();
	}
	
	protected EntityManager getEntityManager() {
		return manager;
	}
	
	protected Session getSession() {
		return manager.unwrap(Session.class);
	}
}