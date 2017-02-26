package com.pvtoc.services;


import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;

import java.util.List;

public interface UserService<T extends User> extends EntityService<T> {
    /**
     * Calls UserDaoImpl getAllClients() method
     *
     * @return <tt>List</tt> of all <tt>User</tt> objects
     * with CLIENT userRole
     * @throws ServiceException
     */
    List<T> getAllClients() throws ServiceException;
    /**
     * Calls UserDaoImpl getPageOfClients() method
     *
     * @param pageNumber     - page number of <tt>User</tt> objects list with <tt>CLIENT</tt> status
     * @param clientsPerPage - number of <tt>User</tt> objects with <tt>CLIENT</tt> status per page
     * @return <tt>List</tt> of <tt>User</tt> objects with <tt>CLIENT</tt> status on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     * @throws ServiceException
     */
    List<T> getPageOfClients(int pageNumber, int clientsPerPage) throws ServiceException;
    /**
     * Calls UserDaoImpl getByCardNumber() method
     *
     * @param login -  <tt>login</tt>  being used to get <tt>User</tt> object
     * @return <tt>User</tt> with <i>login</i> value
     * @throws ServiceException
     */
    T getUserByLogin(String login) throws ServiceException;
    /**
     * Calls UserDaoImpl isNewUser() method
     *
     * @param user <tt>User</tt> object to check if it is new
     * @return true if <tt>User</tt> <i>user</i> exists in the database
     * @throws ServiceException
     */
    boolean checkIsNewUser(T user) throws ServiceException;
    /**
     * Calls UserDaoImpl getNumberOfPagesWithClients() method
     *
     * @param clientsPerPage - get number of <tt>User</tt> objects with <tt>CLIENT</tt> status per page
     * @return number of <tt>User</tt> objects with <tt>CLIENT</tt> status lists
     * @throws ServiceException
     */
    int getNumberOfPagesWithClients(int clientsPerPage) throws ServiceException;
}
