package com.pvtoc.dao.impl;

import com.pvtoc.constants.HqlRequest;
import com.pvtoc.constants.OrderStatus;
import com.pvtoc.dao.GeneralDao;
import com.pvtoc.dao.OrderDao;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.User;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child repository class being used for executing
 * of CRUD operations with <tt>Order</tt> object
 */
@Repository
@NoArgsConstructor
public class OrderDaoImpl extends GeneralDao<Order> implements OrderDao<Order> {
    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * Get all <tt>Order</tt> objects being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>Order</tt> objects being contained in the database
     */
    @Override
    public List<Order> getAll() {
        Query query = getSession().createQuery(HqlRequest.GET_ALL_ORDERS);
        List<Order> orderList = query.list();
        return orderList;
    }

    /**
     * Get all <tt>Order</tt> objects with <i>status</i> value being contained in the database
     *
     * @param status describes value of <tt>status</tt> property being used to determinate <tt>Order</tt> objects list
     * @return <tt>List</tt> of all  <tt>Order</tt> objects with <i>status</i> value being contained in the database
     */
    @Override
    public List<Order> getOrdersListByStatus(OrderStatus status) {
        Query query = getSession().createQuery(HqlRequest.GET_ORDERS_BY_STATUS);
        query.setParameter(0, status);
        List<Order> orderList = query.list();
        return orderList;
    }

    /**
     * Return all <tt>Order</tt> objects of <tt>User</tt> with <i>userId</i> property value with <i>status</i> value being contained in the database
     *
     * @param status describes value of <tt>status</tt> property being used to determinate <tt>Order</tt> objects list of <tt>User</tt> with <i>userId</i> property value
     * @param user  <tt>User</tt> object, whose <i>status</i> value orders are got
     * @return all <tt>Order</tt> objects of <tt>User</tt>  with <i>status</i> value being contained in the database
     */
    @Override
    public List<Order> getClientOrdersListByStatus(OrderStatus status, User user) {
        Query query = getSession().createQuery(HqlRequest.GET_CLIENTS_ORDERS_BY_STATUS);
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
    @Override
    public boolean isRoomFreeForPeriodInOrder(Order order) {
        Query query = getSession().createQuery(HqlRequest.CHECK_IS_FREE_ROOM_FOR_PERIOD_IN_ORDER);
        query.setParameter(0, order.getRoom());
        query.setParameter(1, order.getCheckInDate());
        query.setParameter(2, order.getCheckOutDate());
        long count = (Long) query.uniqueResult();
        boolean isFree = (count == 0);
        return isFree;
    }

}
