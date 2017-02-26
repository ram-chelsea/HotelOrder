package com.pvtoc.services;


import com.pvtoc.dao.Dao;
import com.pvtoc.entities.Entity;
import com.pvtoc.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public abstract class AbstractEntityService<T extends Entity> implements EntityService<T> {
    private static Logger logger = Logger.getLogger(AbstractEntityService.class);
    protected static final String transactionFailedMessage = "Transaction failed: ";
    @Autowired
    Dao<T> dao;

    protected static int getDatesDifferenceInDays(Date from, Date till) {
        final int MILLIS_A_DAY = 24 * 60 * 60 * 1000;
        return ( int ) ((till.getTime() - from.getTime()) / MILLIS_A_DAY);
    }

    /**
     * Calls Dao save() method
     *
     * @param entity - object of derived class Entity
     */
    @Override
    public void add(T entity) throws ServiceException {
        try {
            dao.save(entity);
            logger.info("Save(" + entity.getClass().toString() + "): " + entity);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls Dao getAllClients() method
     *
     * @return list of objects of derived class Entity
     */
    @Override
    public List<T> getAll() throws ServiceException {
        List<T> entityList = new ArrayList<>();
        try {
            entityList = dao.getAll();
            logger.info("Get All Entities");
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return entityList;
    }

    /**
     * Calls Dao get() method
     *
     * @param id - id of entity
     * @return object of derived class Entity
     */
    @Override
    public T get(Class<T> clazz, int id) throws ServiceException {
        T entity;
        try {
            entity = dao.get(clazz, id);
            logger.info("Get: " + id);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return entity;
    }

    /**
     * Calls Dao delete() method
     *
     * @param entity object being used to delete the object from the database
     */
    @Override
    public void delete(T entity) throws ServiceException {
        try {
            dao.delete(entity);
            logger.info("Delete(" + entity.getClass().toString() + "): " + entity);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

}
