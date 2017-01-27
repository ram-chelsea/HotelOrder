package com.pvt.services.impl;


import com.pvt.dao.impl.UserDaoImpl;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.GeneralService;
import com.pvt.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends GeneralService<User> {
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);
    /**
     * Singleton object of <tt>UserServiceImpl</tt> class
     */
    private static UserServiceImpl instance;
    private static UserDaoImpl userDaoInst = UserDaoImpl.getInstance();
    public static HibernateUtil util = HibernateUtil.getHibernateUtil();

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
     * @throws ServiceException
     */
    @Override
    public void add(User user) throws ServiceException {
        try {
            util.getSession().beginTransaction();
            userDaoInst.save(user);
            util.getSession().getTransaction().commit();
            logger.info("Save(user):" + user);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls UserDaoImpl getAll() method
     *
     * @return <tt>List</tt> of all <tt>User</tt> objects
     * with CLIENT userRole
     * @throws ServiceException
     */
    @Override
    public List<User> getAll() throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            util.getSession().beginTransaction();
            userList = userDaoInst.getAll();
            util.getSession().getTransaction().commit();
            logger.info("Get All Clients");
        } catch (HibernateException e) {
            util.getSession().getTransaction().commit();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return userList;
    }

    @Override
    public User getById(int id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls UserDaoImpl checkUserAuthentication() method
     *
     * @param login    <tt>User</tt> object <tt>login</tt> property to get the object
     * @param password <tt>User</tt> object <tt>password</tt> property to get the object
     * @return true if <tt>User</tt> with <i>login</i> and <i>password</i> is authenticated
     * @throws ServiceException
     */
    public boolean checkUserAuthentication(String login, String password) throws ServiceException {
        boolean isAuthenticated;
        try {
            util.getSession().beginTransaction();
            isAuthenticated = userDaoInst.checkUserAuthentication(login, password);
            util.getSession().getTransaction().commit();
            logger.info("checkUserAuthentication(login, password): " + login);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isAuthenticated;
    }

    /**
     * Calls UserDaoImpl getByCardNumber() method
     *
     * @param login -  <tt>login</tt>  being used to get <tt>User</tt> object
     * @return <tt>User</tt> with <i>login</i> value
     * @throws ServiceException
     */
    public User getUserByLogin(String login) throws ServiceException {
        User user;
        try {
            util.getSession().beginTransaction();
            user = userDaoInst.getByLogin(login);
            util.getSession().getTransaction().commit();
            logger.info("getUserByLogin(login): " + login);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    /**
     * Calls UserDaoImpl isNewUser() method
     *
     * @param user <tt>User</tt> object to check if it is new
     * @return true if <tt>User</tt> <i>user</i> exists in the database
     * @throws ServiceException
     */
    public boolean checkIsNewUser(User user) throws ServiceException {
        boolean isNew = false;
        try {
            util.getSession().beginTransaction();
            if ((userDaoInst.getById(user.getUserId()) == null) & (userDaoInst.isNewUser(user.getLogin()))) {
                isNew = true;
            }
            util.getSession().getTransaction().commit();
            logger.info("checkIsNewUser(user): " + user);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isNew;
    }

}
