package com.pvt.dao;

import com.pvt.entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Describes the abstract class of DAO layer implementing <tt>DAO</tt> interface. It is used as a pattern for concrete implementation classes
 * @param <T> - <tt>Entity</tt>-child class object, being processed in the CRUD operations
 *
 */
public abstract class GeneralDao<T extends Entity> implements Dao<T> {
    /**
     * JDBC-Objects properties, being used in implementation classes for executing
     * of CRUD operations
     */
    protected Connection connection;
    protected PreparedStatement statement;
    protected ResultSet result;
}
