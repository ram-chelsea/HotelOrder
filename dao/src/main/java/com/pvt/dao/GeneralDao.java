package com.pvt.dao;

import com.pvt.entities.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Describes the abstract class of DAO layer implementing <tt>DAO</tt> interface. It is used as a pattern for concrete implementation classes
 *
 * @param <T> - <tt>Entity</tt>-child class object, being processed in the CRUD operations
 */
public abstract class GeneralDao<T extends Entity> implements Dao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    @Override
    public T get(Class<T> clazz, int id) {
        T entity = ( T ) getSession().get(clazz, id);
        return entity;
    }
    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }
    @Override
    public void save(T entity) {
        getSession().save(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

}
