package com.pvtoc.dao;


import com.pvtoc.constants.OrderStatus;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.User;

import java.util.List;
/**
 * Describes the interface of DAO layer being used in the projects
 * to implement CRUD operations with <tt>Order</tt> entities
 *
 * @param <T> - <tt>Order</tt>-child class object, being processed in the CRUD operations
 */
public interface OrderDao<T extends Order> extends Dao<T> {
    /**
     * Get all <tt>Order</tt> objects with <i>status</i> value being contained in the database
     *
     * @param status describes value of <tt>status</tt> property being used to determinate <tt>Order</tt> objects list
     * @return <tt>List</tt> of all  <tt>Order</tt> objects with <i>status</i> value being contained in the database
     */
    List<T> getOrdersListByStatus(OrderStatus status);
    /**
     * Return all <tt>Order</tt> objects of <tt>User</tt> with <i>userId</i> property value with <i>status</i> value being contained in the database
     *
     * @param status describes value of <tt>status</tt> property being used to determinate <tt>Order</tt> objects list of <tt>User</tt> with <i>userId</i> property value
     * @param user  <tt>User</tt> object, whose <i>status</i> value orders are got
     * @return all <tt>Order</tt> objects of <tt>User</tt>  with <i>status</i> value being contained in the database
     */
    List<T> getClientOrdersListByStatus(OrderStatus status, User user);
    /**
     * Checking if the <tt>Order</tt> object <tt>room</tt> field is free for period defined by
     * <tt>checkInDate</tt> and tt>checkOutDate</tt> fields
     *
     * @param order determines the <tt>Order</tt> object <tt>room</tt> field is free for period defined by
     *              <tt>checkInDate</tt> and tt>checkOutDate</tt> fields
     * @return true if the <tt>room</tt> is free for the period
     */
    boolean isRoomFreeForPeriodInOrder(T order);
}
