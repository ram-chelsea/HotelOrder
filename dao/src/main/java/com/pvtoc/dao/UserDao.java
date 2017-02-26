package com.pvtoc.dao;


import com.pvtoc.entities.User;

import java.util.List;
/**
 * Describes the interface of DAO layer being used in the projects
 * to implement CRUD operations with <tt>User</tt> entities
 *
 * @param <T> - <tt>User</tt>-child class object, being processed in the CRUD operations
 */
public interface UserDao<T extends User> extends Dao<T> {
    /**
     * Get all <tt>User</tt> objects with <i>CLIENT</i> orderStatus property being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>User</tt> objects with <i>CLIENT</i> orderStatus property
     * being contained in the database
     */
    List<T> getAllClients();
    /**
     * Returns list of rooms objects on the pageNumber with roomsPerPage
     *
     * @param pageNumber     - page number of <tt>User</tt> with <tt>CLIENT</tt> status objects list
     * @param clientsPerPage - number of <tt>User</tt> with <tt>CLIENT</tt> status objects rooms per page
     * @return <tt>List</tt> of <tt>User</tt> objects with <tt>CLIENT</tt> status on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     */
    List<T> getPageOfClients(int pageNumber, int clientsPerPage);
    /**
     * Get <tt>User</tt> object with <i>login</i> value being contained in the database
     *
     * @param login describes value of <tt>login</tt> property being used to determinate <tt>User</tt> object
     * @return <tt>User</tt> object with <i>login</i> value being contained in the database
     */
    T getByLogin(String login);
    /**
     * Checks if the <tt>User</tt> with the corresponding <i>login</i> hasn't existed
     * in the database
     *
     * @param login being used for checking if the <tt>User</tt> object
     *              with the corresponding <tt>login</tt> property value doesn't exist in the database
     * @return true if <tt>User</tt> with <i>login</i> doesn't exist in the database
     */
    boolean isNewUser(String login);
    /**
     * Returns number of pages with clients in dependence of <i>clientsPerPage</i>
     *
     * @param clientsPerPage number of <tt>User</tt> objects clients  per page
     * @return number of <tt>User</tt> objects with <tt>CLIENT</tt> status lists
     */
    int getNumberOfPagesWithClients(int clientsPerPage);

}
