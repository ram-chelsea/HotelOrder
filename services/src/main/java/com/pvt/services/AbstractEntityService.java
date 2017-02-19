package com.pvt.services;


import com.pvt.dao.Dao;
import com.pvt.entities.Entity;
import com.pvt.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void delete(T entity) throws ServiceException {
        try{
            dao.delete(entity);
            logger.info("Delete(" + entity.getClass().toString() + "): " + entity);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

}
