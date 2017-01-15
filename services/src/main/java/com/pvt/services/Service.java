package com.pvt.services;

import com.pvt.entities.Entity;
import com.pvt.exceptions.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface Service<T extends Entity> {
    /**
     * Calls Dao add() method
     *
     * @param entity - object of derived class Entity
     * @throws SQLException
     */
    void add(T entity) throws SQLException, ServiceException;

    /**
     * Calls Dao getAll() method
     *
     * @return list of objects of derived class Entity
     * @throws SQLException
     */
    List<T> getAll() throws SQLException, ServiceException;

    /**
     * Calls Dao getById() method
     *
     * @param id - id of entity
     * @return object of derived class Entity
     * @throws SQLException
     */
    T getById(int id) throws SQLException, ServiceException;

}
