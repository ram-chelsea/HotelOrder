package com.pvtoc.services;


import com.pvtoc.constants.OrderStatus;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;

import java.util.List;

public interface OrderService<T extends Order> extends EntityService<T> {
    /**
     * Calls OrderDaoImpl getOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @return <tt>List</tt> of <tt>Order</tt> objects with <i>status</i> value
     * @throws ServiceException
     */
    List<T> getOrdersListByStatus(OrderStatus status) throws ServiceException;
    /**
     * Calls OrderDaoImpl getClientOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @param user   - determinate the <tt>User</tt> object whose orders are requested
     * @return <tt>List</tt> of <tt>Order</tt> objects of <tt>User</tt> with<i>userId</i> id and <i>status</i> value
     * @throws ServiceException
     */
    List<T> getClientOrdersListByStatus(OrderStatus status, User user) throws ServiceException;
    /**
     * Calls OrderDaoImpl checkIsRoomFreeForPeriodInOrder() method
     *
     * @param order - <tt>Order</tt> object, which <tt>room</tt> property is checked
     *              for being free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     * @return true if the <tt>room</tt> is free for the period
     * @throws ServiceException
     */
    boolean checkIsRoomFreeForPeriodInOrder(T order) throws ServiceException;
    /**
     * Calls OrderDaoImpl updateOrderStatus() method
     *
     * @param orderId     - orderId determinate the <tt>Order</tt> object to update <tt>status</tt>
     * @param orderStatus -  value to update <tt>Order</tt> object <tt>status</tt> property
     * @throws ServiceException
     */
    void updateOrderStatus(int orderId, OrderStatus orderStatus) throws ServiceException;
    /**
     * Calls OrderDaoImpl add() method if OrderDaoImpl checkIsRoomFreeForPeriodInOrder() method returns true
     *
     * @param order <tt>Order</tt> object,which will be pushed to DB if it's <tt>room</tt> property
     *              is free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     * @return true if <tt>Order</tt> object's <tt>room</tt> property
     * is free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     * @throws ServiceException
     */
    boolean createOrderIfRoomIsFree(T order) throws ServiceException;
}
