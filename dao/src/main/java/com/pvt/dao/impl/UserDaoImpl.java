package com.pvt.dao.impl;

import com.pvt.constants.HqlRequest;
import com.pvt.dao.GeneralDao;
import com.pvt.dao.UserDao;
import com.pvt.entities.User;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>User</tt> object
 */
@Repository
@NoArgsConstructor
public class UserDaoImpl extends GeneralDao<User> implements UserDao<User> {

    /**
     * Get all <tt>User</tt> objects being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>User</tt> objects being contained in the database
     */
    @Override
    public List<User> getAllClients() {
        Query query = getSession().createQuery(HqlRequest.GET_ALL_CLIENTS);
        List<User> userList = query.list();
        return userList;
    }

    @Override
    public List<User> getAll() {
        Query query = getSession().createQuery(HqlRequest.GET_ALL);
        List<User> userList = query.list();
        return userList;
    }

    /**
     * Returns list of rooms objects on the pageNumber with roomsPerPage
     *
     * @param pageNumber     - page number of <tt>User</tt> with <tt>CLIENT</tt> status objects list
     * @param clientsPerPage - number of <tt>User</tt> with <tt>CLIENT</tt> status objects rooms per page
     * @return <tt>List</tt> of <tt>User</tt> objects with <tt>CLIENT</tt> status on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     */
    @Override
    public List<User> getPageOfClients(int pageNumber, int clientsPerPage) {
        int currentIndex = (pageNumber - 1) * clientsPerPage;
        Query query = getSession().createQuery(HqlRequest.GET_ALL_CLIENTS)
                .setFirstResult(currentIndex)
                .setMaxResults(clientsPerPage);
        List<User> userList = query.list();
        return userList;
    }

    /**
     * Get <tt>User</tt> object with <i>login</i> value being contained in the database
     *
     * @param login describes value of <tt>login</tt> property being used to determinate <tt>User</tt> object
     * @return <tt>User</tt> object with <i>login</i> value being contained in the database
     */
    @Override
    public User getByLogin(String login) {
        Query query = getSession().createQuery(HqlRequest.GET_USER_BY_LOGIN)
                .setCacheable(true);
        query.setParameter(0, login);
        User user = ( User ) query.uniqueResult();
        return user;
    }

    /**
     * Checks if the <tt>User</tt> with the corresponding <i>login</i> hasn't existed
     * in the database
     *
     * @param login being used for checking if the <tt>User</tt> object
     *              with the corresponding <tt>login</tt> property value doesn't exist in the database
     * @return true if <tt>User</tt> with <i>login</i> doesn't exist in the database
     */
    @Override
    public boolean isNewUser(String login) {
        Query query = getSession().createQuery(HqlRequest.CHECK_LOGIN);
        query.setParameter(0, login);
        long count = ( Long ) query.uniqueResult();
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
    @Override
    public boolean checkUserAuthentication(String login, String password) {
        Query query = getSession().createQuery(HqlRequest.CHECK_AUTHENTICATION);
        query.setParameter(0, login);
        query.setParameter(1, password);
        long count = ( Long ) query.uniqueResult();
        boolean isSiqnedUp = (count != 0);
        return isSiqnedUp;
    }

    /**
     * Returns number of pages with clients in dependence of <i>clientsPerPage</i>
     *
     * @param clientsPerPage number of <tt>User</tt> objects clients  per page
     * @return number of <tt>User</tt> objects with <tt>CLIENT</tt> status lists
     */
    @Override
    public int getNumberOfPagesWithClients(int clientsPerPage) {
        Query query = getSession().createQuery(HqlRequest.GET_ALL_CLIENTS_NUMBER);
        int numberOfClients = (( Long ) query.uniqueResult()).intValue();
        int numberOfPages = (numberOfClients - 1) / clientsPerPage + 1;
        return numberOfPages;
    }
}

