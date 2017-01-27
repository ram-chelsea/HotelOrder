package com.pvt.services.impl;


import com.pvt.constants.OrderStatus;
import com.pvt.dao.impl.OrderDaoImpl;
import com.pvt.entities.Order;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.GeneralService;
import com.pvt.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderServiceImpl extends GeneralService<Order> {
    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
    /**
     * Singleton object of <tt>OrderServiceImpl</tt> class
     */
    private static OrderServiceImpl instance;
    private static OrderDaoImpl orderDaoInst = OrderDaoImpl.getInstance();
    private Transaction transaction;
    public static HibernateUtil util = HibernateUtil.getHibernateUtil();

    /**
     * Creates a OrderServiceImpl variable
     */
    private OrderServiceImpl() {
    }

    /**
     * Describes synchronized method of getting <tt>OrderServiceImpl</tt> singleton object
     *
     * @return <tt>OrderServiceImpl</tt> singleton object
     */
    public static synchronized OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    /**
     * Calls OrderDaoImpl add() method
     *
     * @param order - <tt>Order</tt> object to add
     * @throws ServiceException
     */
    @Override
    public void add(Order order) throws ServiceException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            orderDaoInst.save(order);
            transaction.commit();
            logger.info("Save(order):" + order);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls OrderDaoImpl getAll() method
     *
     * @return <tt>List</tt> of all <tt>Order</tt> objects
     * @throws ServiceException
     */
    @Override
    public List<Order> getAll() throws ServiceException {
        List<Order> ordersList;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            ordersList = orderDaoInst.getAll();
            transaction.commit();
            logger.info("Get All Orders ");
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return ordersList;
    }

    /**
     * Calls OrderDaoImpl getById() method
     *
     * @param orderId - <tt>Order</tt> object <tt>orderId</tt> property to get the object
     * @return <tt>Order</tt> with <i>orderId</i> id value
     * @throws ServiceException
     */
    @Override
    public Order getById(int orderId) throws ServiceException {
        Order order;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            order = orderDaoInst.getById(orderId);
            transaction.commit();
            logger.info("GetById(orderId): " + orderId);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return order;
    }

    /**
     * Calls OrderDaoImpl getOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @return <tt>List</tt> of <tt>Order</tt> objects with <i>status</i> value
     * @throws ServiceException
     */
    public List<Order> getOrdersListByStatus(OrderStatus status) throws ServiceException {
        List<Order> ordersList;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            ordersList = orderDaoInst.getOrdersListByStatus(status);
            transaction.commit();
            logger.info("getOrdersListByStatus(status): " + status);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return ordersList;
    }

    /**
     * Calls OrderDaoImpl getClientOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @param userId - userId determinate the <tt>User</tt> object whose orders are requested
     * @return <tt>List</tt> of <tt>Order</tt> objects of <tt>User</tt> with<i>userId</i> id and <i>status</i> value
     * @throws ServiceException
     */
    public List<Order> getClientOrdersListByStatus(OrderStatus status, int userId) throws ServiceException {
        List<Order> ordersList;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            ordersList = orderDaoInst.getClientOrdersListByStatus(status, userId);
            transaction.commit();
            logger.info("getClientOrdersListByStatus(status, userId):" + status + ", " + userId);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return ordersList;
    }

    /**
     * Calls OrderDaoImpl updateOrderStatus() method
     *
     * @param orderId     - orderId determinate the <tt>Order</tt> object to update <tt>status</tt>
     * @param orderStatus -  value to update <tt>Order</tt> object <tt>status</tt> property
     * @throws ServiceException
     */
    public void updateOrderStatus(int orderId, OrderStatus orderStatus) throws ServiceException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            orderDaoInst.updateOrderStatus(orderId, orderStatus);
            transaction.commit();
            logger.info("updateOrderStatus( orderId, orderStatus): " + orderId + ", " + orderStatus);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

}
