package com.pvt.services.impl;


import com.pvt.dao.impl.UserDao;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.AbstractEntityService;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends AbstractEntityService<User> {
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);
    /**
     * Singleton object of <tt>UserServiceImpl</tt> class
     */
    private static UserServiceImpl instance;

    private UserDao userDao=UserDao.getInstance();

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
     * Calls UserDao add() method
     *
     * @param user - <tt>User</tt> object to add
     * @throws ServiceException
     */
    @Override
    public void add(User user) throws ServiceException {
        try {
            util.getSession().beginTransaction();
            userDao.save(user);
            util.getSession().getTransaction().commit();
            logger.info("Save(user):" + user);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls UserDao getAllClients() method
     *
     * @return <tt>List</tt> of all <tt>User</tt> objects
     * with CLIENT userRole
     * @throws ServiceException
     */
    public List<User> getAllClients() throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            util.getSession().beginTransaction();
            userList = userDao.getAllClients();
            util.getSession().getTransaction().commit();
            logger.info("Get All Clients");
        } catch (HibernateException e) {
            util.getSession().getTransaction().commit();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return userList;
    }

    /**
     * Calls UserDao getAllClients() method
     *
     * @return <tt>List</tt> of all <tt>User</tt> objects
     * @throws ServiceException
     */
    public List<User> getAll() throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            util.getSession().beginTransaction();
            userList = userDao.getAll();
            util.getSession().getTransaction().commit();
            logger.info("Get All Clients");
        } catch (HibernateException e) {
            util.getSession().getTransaction().commit();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return userList;
    }

    /**
     * Calls UserDao getPageOfClients() method
     *
     * @param pageNumber     - page number of <tt>User</tt> objects list with <tt>CLIENT</tt> status
     * @param clientsPerPage - number of <tt>User</tt> objects with <tt>CLIENT</tt> status per page
     * @return <tt>List</tt> of <tt>User</tt> objects with <tt>CLIENT</tt> status on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     * @throws ServiceException
     */
    public List<User> getPageOfClients(int pageNumber, int clientsPerPage) throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            util.getSession().beginTransaction();
            userList = userDao.getPageOfClients(pageNumber, clientsPerPage);
            util.getSession().getTransaction().commit();
            logger.info("Get Clients For Page Number " + pageNumber);
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
     * Calls UserDao checkUserAuthentication() method
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
            isAuthenticated = userDao.checkUserAuthentication(login, password);
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
     * Calls UserDao getByCardNumber() method
     *
     * @param login -  <tt>login</tt>  being used to get <tt>User</tt> object
     * @return <tt>User</tt> with <i>login</i> value
     * @throws ServiceException
     */
    public User getUserByLogin(String login) throws ServiceException {
        User user;
        try {
            util.getSession().beginTransaction();
            user = userDao.getByLogin(login);
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
     * Calls UserDao isNewUser() method
     *
     * @param user <tt>User</tt> object to check if it is new
     * @return true if <tt>User</tt> <i>user</i> exists in the database
     * @throws ServiceException
     */
    public boolean checkIsNewUser(User user) throws ServiceException {
        boolean isNew = false;
        try {
            util.getSession().beginTransaction();
            if (userDao.isNewUser(user.getLogin())) {
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

    /**
     * Calls UserDao getNumberOfPagesWithClients() method
     *
     * @param clientsPerPage - get number of <tt>User</tt> objects with <tt>CLIENT</tt> status per page
     * @return number of <tt>User</tt> objects with <tt>CLIENT</tt> status lists
     * @throws ServiceException
     */
    public int getNumberOfPagesWithClients(int clientsPerPage) throws ServiceException {
        int numberOfPages;
        try {
            util.getSession().beginTransaction();
            numberOfPages = userDao.getNumberOfPagesWithClients(clientsPerPage);
            util.getSession().getTransaction().commit();
            logger.info("getNumberOfPagesWithClients(clientsPerPage): " + clientsPerPage);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return numberOfPages;
    }
}
