package com.pvtoc.dao;

import com.pvtoc.entities.Entity;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Describes the abstract class of DAO layer implementing <tt>DAO</tt> interface. It is used as a pattern for concrete implementation classes
 *
 * @param <T> - <tt>Entity</tt>-child class object, being processed in the CRUD operations
 */
@Repository
@Data
public abstract class GeneralDao<T extends Entity> implements Dao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * Get Hibernate session from SessionFactory
     *
     * @return Hibernate Session object
     */
    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Returns the Object of <tt>Entity</tt>-child class from the database by its <i>id</i> value
     *
     * @param id value of object id property being used to get the object from the database
     * @return Object, having corresponding <i>id</i> value
     */
    @Override
    public T get(Class<T> clazz, int id) {
        T entity = ( T ) getSession().get(clazz, id);
        return entity;
    }

    /**
     * Delete the Object of <tt>Entity</tt>-child class from the database
     *
     * @param entity object being used to delete the object from the database
     */
    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    /**
     * Saves the entity object properties values in database
     *
     * @param entity element, which properties will be pushed into the database
     */
    @Override
    public void save(T entity) {
        getSession().save(entity);
    }

    /**
     * Update the Object of <tt>Entity</tt>-child class in the database
     *
     * @param entity object being used to update
     */
    @Override
    public void update(T entity) {
        getSession().update(entity);
    }

}
