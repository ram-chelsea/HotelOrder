package com.pvt.dao.impl;

import com.pvt.constants.HqlRequest;
import com.pvt.constants.OrderStatus;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.Order;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>Order</tt> object
 */
public class OrderDaoImpl extends GeneralDao<Order> {

    /**
     * Singleton object of <tt>OrderDaoImpl</tt> class
     */
    private static OrderDaoImpl instance;

    /**
     * Creates a OrderDaoImpl variable
     */
    private OrderDaoImpl() {
    }

    /**
     * Describes synchronized method of getting <tt>OrderDaoImpl</tt> singleton object
     *
     * @return <tt>OrderDaoImpl</tt> singleton object
     */
    public static synchronized OrderDaoImpl getInstance() {
        if (instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    /**
     * Adds the <tt>Order</tt> object properties values into database
     *
     * @param order <tt>Order</tt> element, which properties will be pushed into the database
     */
    public void save(Order order) {
        util.getSession().save(order);
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
     * Returns the Object of <tt>Order</tt> class from the database by its <i>orderId</i> value
     *
     * @param orderId value of <tt>Order</tt> property being used to get the object from the database
     * @return <tt>Order</tt> object, having corresponding <i>orderId</i> value
     */
    public Order getById(int orderId) {
        Session session = util.getSession();
        Order order = (Order) session.get(Order.class, orderId);
        return order;
    }

    /**
     * Delete the Object of <tt>Order</tt> class from the database by <i>orderId</i> value
     *
     * @param orderId value of <tt>Order</tt> property being used to delete the corresponding object from the database
     */
    public void delete(int orderId) {
        Session session = util.getSession();
        Order order = (Order) session.get(Order.class, orderId);
        session.delete(order);
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
    public List<Order> getClientOrdersListByStatus(OrderStatus status, int userId) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_CLIENTS_ORDERS_BY_STATUS);
        query.setParameter(0, status);
        query.setParameter(1, userId);
        List<Order> orderList = query.list();
        return orderList;
    }

    /**
     * Update value of <tt>status</tt> property value of <tt>Order</tt> with <i>orderId</i> property value
     * to <i>orderStatus</i> value
     *
     * @param orderId        determines <tt>Order</tt> object, whose <i>status</i> value is updated
     * @param newOrderStatus determines value of <tt>status</tt> property to what <tt>Order</tt> object <tt>status</tt> property value is changed
     */
    public void updateOrderStatus(int orderId, OrderStatus newOrderStatus) {
        Session session = util.getSession();
        Order order = (Order) session.get(Order.class, orderId);
        order.setOrderStatus(newOrderStatus);
        session.update(order);
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
