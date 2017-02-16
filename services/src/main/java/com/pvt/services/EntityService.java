package com.pvt.services;

import com.pvt.entities.Entity;
import com.pvt.exceptions.ServiceException;

import java.util.List;

public interface EntityService<T extends Entity> {
    /**
     * Calls Dao save() method
     *
     * @param entity - object of derived class Entity
     */
    void add(T entity) throws ServiceException;

    /**
     * Calls Dao getAllClients() method
     *
     * @return list of objects of derived class Entity
     */
    List<T> getAll() throws ServiceException;

    /**
     * Calls Dao get() method
     *
     * @param id - id of entity
     * @return object of derived class Entity
     */
    T getById(int id) throws ServiceException;

}
