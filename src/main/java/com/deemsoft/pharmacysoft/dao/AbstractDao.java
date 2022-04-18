package com.deemsoft.pharmacysoft.dao;

import java.io.Serializable;
import java.io.InputStream;

import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import java.sql.Blob;
import org.hibernate.Hibernate;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

public abstract class AbstractDao<PK extends Serializable, T> {
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}
	public void save(T entity) {
		getSession().save(entity);
	}
	public void delete(T entity) {
		
		getSession().delete(entity);
	}
	
	public int iud_query(String sql) {
		Query query = getSession().createQuery(sql);
		int result = query.executeUpdate();
		return result;
		
	}
	
	public List select_query(String sql) {
		Query query = getSession().createQuery(sql);
		return query.list();
		
	}
	public List sqlQuery(String sql){
		Query query=getSession().createSQLQuery(sql);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> aliasToValueMapList=query.list();
		return aliasToValueMapList;
	}
	protected Criteria createEntityCriteria(){
		return getSession().createCriteria(persistentClass);
	}
	
	protected Blob getBlob(InputStream ioStream, long fSize){
		//Blob blob = Hibernate.createBlob(ioStream,fSize,getSession());		
		Blob blob = Hibernate.getLobCreator(getSession()).createBlob(ioStream,fSize);
		return blob;
	}

}
