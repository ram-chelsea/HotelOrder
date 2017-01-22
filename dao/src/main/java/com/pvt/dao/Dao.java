package com.pvt.dao;

import com.pvt.entities.Entity;
import com.pvt.exceptions.DaoException;

import java.util.List;

/**
 * Describes the interface of DAO layer being used in the projects
 * to implement CRUD operations with entities
 *
 * @param <T> - <tt>Entity</tt>-child class object, being processed in the CRUD operations
 */
public interface Dao<T extends Entity> {
    /**
     * Adds the entity object properties values into database
     *
     * @param entity element, which properties will be pushed into the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    void add(T entity) throws DaoException;

    /**
     * Returns <tt>List</tt> of all <tt>Entity</tt>-child class objects from the corresponding database table
     *
     * @return <<tt>List</tt> of all <tt>Entity</tt>-child class objects from the corresponding database table
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    List<T> getAll() throws DaoException;

    /**
     * Returns the Object of <tt>Entity</tt>-child class from the database by its <i>id</i> value
     *
     * @param id value of object id property being used to get the object from the database
     * @return Object, having corresponding <i>id</i> value
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    T getById(int id) throws DaoException;

    /**
     * Delete the Object of <tt>Entity</tt>-child class from the database by its <i>id</i> value
     *
     * @param id value of object id property being used to delete the object from the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    void delete(int id) throws DaoException;

}

