package com.pvt.dao;

import com.pvt.entities.Entity;
import com.pvt.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Describes the abstract class of DAO layer implementing <tt>DAO</tt> interface. It is used as a pattern for concrete implementation classes
 * @param <T> - <tt>Entity</tt>-child class object, being processed in the CRUD operations
 *
 */
public abstract class GeneralDao<T extends Entity> implements Dao<T> {

    protected static HibernateUtil util = HibernateUtil.getHibernateUtil();

    public T get(Class<T> clazz, int id) {
        Session session = util.getSession();
        T entity = (T) session.get(clazz, id);
        return entity;
    }

    public void delete(T entity) {
        Session session = util.getSession();
        session.delete(entity);
    }

    public void save(T entity) {
        Session session = util.getSession();
        session.save(entity);
    }
    public void update(T entity){
        Session session = util.getSession();
        session.update(entity);
    }

}
