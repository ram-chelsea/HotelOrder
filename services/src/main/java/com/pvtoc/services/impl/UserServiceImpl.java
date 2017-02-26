package com.pvtoc.services.impl;


import com.pvtoc.dao.UserDao;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.AbstractEntityService;
import com.pvtoc.services.UserService;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@Transactional
public class UserServiceImpl extends AbstractEntityService<User> implements UserService<User> {
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    UserDao userDao;

    /**
     * Calls UserDaoImpl getAllClients() method
     *
     * @return <tt>List</tt> of all <tt>User</tt> objects
     * with ROLE_CLIENT userRole
     * @throws ServiceException
     */
    @Override
    public List<User> getAllClients() throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            userList = userDao.getAllClients();
            logger.info("Get All Clients");
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return userList;
    }

    /**
     * Calls UserDaoImpl getPageOfClients() method
     *
     * @param pageNumber     - page number of <tt>User</tt> objects list with <tt>ROLE_CLIENT</tt> status
     * @param clientsPerPage - number of <tt>User</tt> objects with <tt>ROLE_CLIENT</tt> status per page
     * @return <tt>List</tt> of <tt>User</tt> objects with <tt>ROLE_CLIENT</tt> status on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     * @throws ServiceException
     */
    @Override
    public List<User> getPageOfClients(int pageNumber, int clientsPerPage) throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            userList = userDao.getPageOfClients(pageNumber, clientsPerPage);
            logger.info("Get Clients For Page Number " + pageNumber);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return userList;
    }

    @Override
    public User get(Class<User> clazz, int id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls UserDaoImpl getByCardNumber() method
     *
     * @param login -  <tt>login</tt>  being used to get <tt>User</tt> object
     * @return <tt>User</tt> with <i>login</i> value
     * @throws ServiceException
     */
    @Override
    public User getUserByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDao.getByLogin(login);
            logger.info("getUserByLogin(login): " + login);
        } catch (HibernateException e) {
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
    @Override
    public boolean checkIsNewUser(User user) throws ServiceException {
        boolean isNew = false;
        try {
            if (userDao.isNewUser(user.getLogin())) {
                isNew = true;
            }
            logger.info("checkIsNewUser(user): " + user);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isNew;
    }

    /**
     * Calls UserDaoImpl getNumberOfPagesWithClients() method
     *
     * @param clientsPerPage - get number of <tt>User</tt> objects with <tt>ROLE_CLIENT</tt> status per page
     * @return number of <tt>User</tt> objects with <tt>ROLE_CLIENT</tt> status lists
     * @throws ServiceException
     */
    @Override
    public int getNumberOfPagesWithClients(int clientsPerPage) throws ServiceException {
        int numberOfPages;
        try {
            numberOfPages = userDao.getNumberOfPagesWithClients(clientsPerPage);
            logger.info("getNumberOfPagesWithClients(clientsPerPage): " + clientsPerPage);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return numberOfPages;
    }
}
