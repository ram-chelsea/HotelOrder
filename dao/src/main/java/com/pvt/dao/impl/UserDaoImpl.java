package com.pvt.dao.impl;

import com.pvt.constants.ColumnName;
import com.pvt.constants.SqlRequest;
import com.pvt.constants.UserRole;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.User;
import com.pvt.exceptions.DaoException;
import com.pvt.managers.PoolManager;
import com.pvt.util.EntityBuilder;
import com.pvt.utils.ProjectLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>User</tt> object
 */
public class UserDaoImpl extends GeneralDao<User> {
    /**
     * String property being used for describing the error in case of SQL Exception for <i>Log4j</i>
     */
    private static String message;
    /**
     * Singleton object of <tt>UserDaoImpl</tt> class
     */
    private static UserDaoImpl instance;

    /**
     * Creates a UserDaoImpl variable
     */
    private UserDaoImpl() {
    }

    /**
     * Describes synchronized method of getting <tt>UserDaoImpl</tt> singleton object
     *
     * @return <tt>UserDaoImpl</tt> singleton object
     */
    public static synchronized UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    /**
     * Adds the <tt>User</tt> object properties values into database
     *
     * @param user <tt>User</tt> element, which properties will be pushed into the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public void add(User user) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.ADD_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getUserRole().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to add the user account ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Get all <tt>User</tt> objects being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>User</tt> objects being contained in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public List<User> getAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ALL_CLIENTS);
            result = statement.executeQuery();
            while (result.next()) {
                User user = buildUser(result);
                userList.add(user);
            }
        } catch (SQLException e) {
            message = "Unable to return list of users ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return userList;
    }

    /**
     * Returns the Object of <tt>User</tt> class from the database by its <i>orderId</i> value
     *
     * @param userId value of <tt>User</tt> property being used to get the object from the database
     * @return <tt>User</tt> object, having corresponding <i>userId</i> value
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public User getById(int userId) throws DaoException {
        User user = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_USER_BY_ID);
            statement.setInt(1, userId);
            result = statement.executeQuery();
            while (result.next()) {
                user = buildUser(result);
            }
        } catch (SQLException e) {
            message = "Unable to return the user ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return user;
    }

    /**
     * Get <tt>User</tt> object with <i>login</i> value being contained in the database
     *
     * @param login describes value of <tt>login</tt> property being used to determinate <tt>User</tt> object
     * @return <tt>User</tt> object with <i>login</i> value being contained in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public User getByLogin(String login) throws DaoException {
        User user = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_USER_BY_LOGIN);
            statement.setString(1, login);
            result = statement.executeQuery();
            while (result.next()) {
                user = buildUser(result);
            }
        } catch (SQLException e) {
            message = "Unable to return the user ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return user;
    }

    /**
     * Delete the Object of <tt>User</tt> class from the database by <i>userId</i> value
     *
     * @param userId value of <tt>User</tt> property being used to delete the corresponding object from the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public void delete(int userId) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.DELETE_USER_BY_ID);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to delete the user ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Checks if the <tt>User</tt> with the corresponding <i>login</i> hasn't existed
     * in the database
     *
     * @param login being used for checking if the <tt>User</tt> object
     *              with the corresponding <tt>login</tt> property value doesn't exist in the database
     * @return true if <tt>User</tt> with <i>login</i> doesn't exist in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public boolean isNewUser(String login) throws DaoException {
        boolean isNew = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.CHECK_LOGIN);
            statement.setString(1, login);
            result = statement.executeQuery();
            if (!result.next()) {
                isNew = true;
            }
        } catch (SQLException e) {
            message = "Unable to check the user ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return isNew;
    }

    /**
     * Checks if the <tt>User</tt> with the corresponding <i>login</i> and <i>password</i> hasn't existed
     * in the database
     *
     * @param login    being used for checking the <tt>User</tt> is authenticated
     * @param password being used for checking the <tt>User</tt> is authenticated
     * @return true if <tt>User</tt> with <i>login</i> and <i>password</i> doesn't exist in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public boolean checkUserAuthentication(String login, String password) throws DaoException {
        boolean isSiqnedUp = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.CHECK_AUTHENTICATION);
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery();
            if (result.next()) {
                isSiqnedUp = true;
            }
        } catch (SQLException e) {
            message = "Unable to check the user ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return isSiqnedUp;
    }

    /**
     * Build <tt>User</tt> object with the properties values corresponding
     * to ResultSet being got after SQL Request executing
     *
     * @param result <tt>ResultSet</tt> being got after SQL Request executing
     * @return <tt>User</tt> object with the properties values corresponding
     * to ResultSet being got after SQL Request executing
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    private User buildUser(ResultSet result) throws SQLException {

        int userId = result.getInt(ColumnName.USER_ID);
        String firstName = result.getString(ColumnName.USER_FIRST_NAME);
        String lastName = result.getString(ColumnName.USER_LAST_NAME);
        String login = result.getString(ColumnName.USER_LOGIN);
        String password = result.getString(ColumnName.USER_PASSWORD);
        UserRole userRole = UserRole.valueOf(result.getString(ColumnName.USER_ROLE));
        return EntityBuilder.buildUser(userId, login, firstName, lastName, password, userRole);
    }


}

