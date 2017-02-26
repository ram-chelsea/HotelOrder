package com.pvtoc.services;

import com.pvtoc.entities.Entity;
import com.pvtoc.exceptions.ServiceException;

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
    T get(Class<T> clazz, int id) throws ServiceException;

    /**
     * Calls Dao delete() method
     *
     * @param entity object being used to delete the object from the database
     */
    void delete(T entity) throws ServiceException;


}
