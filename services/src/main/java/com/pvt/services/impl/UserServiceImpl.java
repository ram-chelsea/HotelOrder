package com.pvt.services.impl;


import com.pvt.dao.impl.UserDaoImpl;
import com.pvt.entities.User;
import com.pvt.exceptions.DaoException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PoolManager;
import com.pvt.services.GeneralService;
import com.pvt.utils.ProjectLogger;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl extends GeneralService<User> {
    /**
     * Singleton object of <tt>UserServiceImpl</tt> class
     */
    private static UserServiceImpl instance;

    /**
     * Creates a UserServiceImpl variable
     */
    private UserServiceImpl() {
    }

    /**
     * Describes synchronized method of getting <tt>UserServiceImpl</tt> singleton object
     *
     * @return <tt>UserServiceImpl</tt> singleton object
     */
    public static synchronized UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    /**
     * Calls UserDaoImpl add() method
     *
     * @param user - <tt>User</tt> object to add
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public void add(User user) throws SQLException, ServiceException {
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            UserDaoImpl.getInstance().add(user);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    /**
     * Calls UserDaoImpl getAll() method
     *
     * @return <tt>List</tt> of all <tt>User</tt> objects
     * with CLIENT userRole
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public List<User> getAll() throws SQLException, ServiceException {
        List<User> userList;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            userList = UserDaoImpl.getInstance().getAll();
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return userList;
    }

    @Override
    public User getById(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls UserDaoImpl checkUserAuthentication() method
     *
     * @param login    <tt>User</tt> object <tt>login</tt> property to get the object
     * @param password <tt>User</tt> object <tt>password</tt> property to get the object
     * @return true if <tt>User</tt> with <i>login</i> and <i>password</i> is authenticated
     * @throws SQLException
     * @throws ServiceException
     */
    public boolean checkUserAuthentication(String login, String password) throws SQLException, ServiceException {
        boolean isAuthenticated;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            isAuthenticated = UserDaoImpl.getInstance().checkUserAuthentication(login, password);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return isAuthenticated;
    }

    /**
     * Calls UserDaoImpl getByCardNumber() method
     *
     * @param login -  <tt>login</tt>  being used to get <tt>User</tt> object
     * @return <tt>User</tt> with <i>login</i> value
     * @throws SQLException
     * @throws ServiceException
     */
    public User getUserByLogin(String login) throws SQLException, ServiceException {
        User user;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            user = UserDaoImpl.getInstance().getByLogin(login);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return user;
    }

    /**
     * Calls UserDaoImpl isNewUser() method
     *
     * @param user <tt>User</tt> object to check if it is new
     * @return true if <tt>User</tt> <i>user</i> exists in the database
     * @throws SQLException
     * @throws ServiceException
     */
    public boolean checkIsNewUser(User user) throws SQLException, ServiceException {
        boolean isNew = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            if ((UserDaoImpl.getInstance().getById(user.getUserId()) == null) & (UserDaoImpl.getInstance().isNewUser(user.getLogin()))) {
                isNew = true;
            }
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return isNew;
    }

}