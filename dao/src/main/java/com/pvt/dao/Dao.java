package com.pvt.dao;

import com.pvt.entities.Entity;

import java.util.List;

/**
 * Describes the interface of DAO layer being used in the projects
 * to implement CRUD operations with entities
 *
 * @param <T> - <tt>Entity</tt>-child class object, being processed in the CRUD operations
 */
public interface Dao<T extends Entity> {
    /**
     * Saves the entity object properties values in database
     *
     * @param entity element, which properties will be pushed into the database
     */
    void save(T entity);

    /**
     * Returns <tt>List</tt> of all <tt>Entity</tt>-child class objects from the corresponding database table
     *
     * @return <<tt>List</tt> of all <tt>Entity</tt>-child class objects from the corresponding database table
     */
    List<T> getAll();

    /**
     * Returns the Object of <tt>Entity</tt>-child class from the database by its <i>id</i> value
     *
     * @param id value of object id property being used to get the object from the database
     * @return Object, having corresponding <i>id</i> value
     */
    T get(Class<T> clazz, int id);

    /**
     * Delete the Object of <tt>Entity</tt>-child class from the database
     *
     * @param entity  object being used to delete the object from the database
     */
    void delete(T entity);
    /**
     * Update the Object of <tt>Entity</tt>-child class in the database
     *
     * @param entity  object being used to update
     */
    void update(T entity);



}

