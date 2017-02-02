package com.pvt.dao.impl;

import com.pvt.constants.HqlRequest;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.User;
import com.pvt.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>User</tt> object
 */
public class UserDaoImpl extends GeneralDao<User> {

    /**
     * Singleton object of <tt>UserDaoImpl</tt> class
     */
    private static UserDaoImpl instance;
    private static HibernateUtil util = HibernateUtil.getHibernateUtil();

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
     */
    public void save(User user) {
        Session session = util.getSession();
        session.save(user);
    }

    /**
     * Get all <tt>User</tt> objects being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>User</tt> objects being contained in the database
     */

    public List<User> getAll() {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_ALL_CLIENTS);
        List<User> userList = query.list();
        return userList;
    }

    /**
     * Returns the Object of <tt>User</tt> class from the database by its <i>orderId</i> value
     *
     * @param userId value of <tt>User</tt> property being used to get the object from the database
     * @return <tt>User</tt> object, having corresponding <i>userId</i> value
     */

    public User getById(int userId) {
        Session session = util.getSession();
        User user = (User) session.get(User.class, userId);
        return user;
    }

    /**
     * Get <tt>User</tt> object with <i>login</i> value being contained in the database
     *
     * @param login describes value of <tt>login</tt> property being used to determinate <tt>User</tt> object
     * @return <tt>User</tt> object with <i>login</i> value being contained in the database
     */
    public User getByLogin(String login) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_USER_BY_LOGIN)
                .setCacheable(true);
        query.setParameter(0, login);
        User user = (User) query.uniqueResult();
        return user;
    }//TODO разобраться надо ли setCacheale

    /**
     * Delete the Object of <tt>User</tt> class from the database
     *
     * @param userId <tt>User</tt> element property,by  which the element is deleted from the database
     */

    public void delete(int userId) {
        Session session = util.getSession();
        User user = (User) session.get(User.class, userId);
        session.delete(user);
    }

    /**
     * Checks if the <tt>User</tt> with the corresponding <i>login</i> hasn't existed
     * in the database
     *
     * @param login being used for checking if the <tt>User</tt> object
     *              with the corresponding <tt>login</tt> property value doesn't exist in the database
     * @return true if <tt>User</tt> with <i>login</i> doesn't exist in the database
     */
    public boolean isNewUser(String login) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.CHECK_LOGIN);
        query.setParameter(0, login);
        int count = ((Long) query.uniqueResult()).intValue();
        boolean isNewUser = (count == 0);
        return isNewUser;
    }

    /**
     * Checks if the <tt>User</tt> with the corresponding <i>login</i> and <i>password</i> hasn't existed
     * in the database
     *
     * @param login    being used for checking the <tt>User</tt> is authenticated
     * @param password being used for checking the <tt>User</tt> is authenticated
     * @return true if <tt>User</tt> with <i>login</i> and <i>password</i>  exists in the database
     */
    public boolean checkUserAuthentication(String login, String password) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.CHECK_AUTHENTICATION);
        query.setParameter(0, login);
        query.setParameter(1, password);
        int count = ((Long) query.uniqueResult()).intValue();
        boolean isSiqnedUp = (count != 0);

        return isSiqnedUp;
    }

}

