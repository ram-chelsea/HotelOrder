package com.pvt.dao.impl;

import com.pvt.constants.HqlRequest;
import com.pvt.constants.OrderStatus;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.Order;
import com.pvt.entities.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>Order</tt> object
 */

public class OrderDao extends GeneralDao<Order> {

    /**
     * Singleton object of <tt>OrderDao</tt> class
     */
    private static OrderDao instance;

    /**
     * Creates a OrderDao variable
     */
    private OrderDao() {
    }

    /**
     * Describes synchronized method of getting <tt>OrderDao</tt> singleton object
     *
     * @return <tt>OrderDao</tt> singleton object
     */
    public static synchronized OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDao();
        }
        return instance;
    }

    /**
     * Get all <tt>Order</tt> objects being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>Order</tt> objects being contained in the database
     */
    public List<Order> getAll() {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_ALL_ORDERS);
        List<Order> orderList = query.list();
        return orderList;
    }


    /**
     * Get all <tt>Order</tt> objects with <i>status</i> value being contained in the database
     *
     * @param status describes value of <tt>status</tt> property being used to determinate <tt>Order</tt> objects list
     * @return <tt>List</tt> of all  <tt>Order</tt> objects with <i>status</i> value being contained in the database
     */
    public List<Order> getOrdersListByStatus(OrderStatus status) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_ORDERS_BY_STATUS);
        query.setParameter(0, status);
        List<Order> orderList = query.list();
        return orderList;
    }

    /**
     * Return all <tt>Order</tt> objects of <tt>User</tt> with <i>userId</i> property value with <i>status</i> value being contained in the database
     *
     * @param status describes value of <tt>status</tt> property being used to determinate <tt>Order</tt> objects list of <tt>User</tt> with <i>userId</i> property value
     * @param userId determinates <tt>User</tt> object, whose <i>status</i> value orders are got
     * @return all <tt>Order</tt> objects of <tt>User</tt> with <i>userId</i> property value with <i>status</i> value being contained in the database
     */
    public List<Order> getClientOrdersListByStatus(OrderStatus status, User user) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_CLIENTS_ORDERS_BY_STATUS);
        query.setParameter(0, status);
        query.setParameter(1, user);
        List<Order> orderList = query.list();
        return orderList;
    }


    /**
     * Checking if the <tt>Order</tt> object <tt>room</tt> field is free for period defined by
     * <tt>checkInDate</tt> and tt>checkOutDate</tt> fields
     *
     * @param order determines the <tt>Order</tt> object <tt>room</tt> field is free for period defined by
     *              <tt>checkInDate</tt> and tt>checkOutDate</tt> fields
     * @return true if the <tt>room</tt> is free for the period
     */
    public boolean isFreeRoomForPeriodInOrder(Order order) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.CHECK_IS_FREE_ROOM_FOR_PERIOD_IN_ORDER);
        query.setParameter(0, order.getRoom());
        query.setParameter(1, order.getCheckInDate());
        query.setParameter(2, order.getCheckOutDate());
        long count = (Long) query.uniqueResult();
        boolean isFree = (count == 0);
        return isFree;
    }

}
