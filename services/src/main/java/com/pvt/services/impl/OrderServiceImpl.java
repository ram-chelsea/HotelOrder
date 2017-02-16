package com.pvt.services.impl;


import com.pvt.constants.OrderStatus;
import com.pvt.dao.impl.OrderDao;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.AbstractEntityService;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl extends AbstractEntityService<Order> {
    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
    /**
     * Singleton object of <tt>OrderServiceImpl</tt> class
     */
    private static OrderServiceImpl instance;
    private static OrderDao orderDaoInst = OrderDao.getInstance();

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
     * Calls OrderDao add() method
     *
     * @param order - <tt>Order</tt> object to add
     * @throws ServiceException
     */
    @Override
    public void add(Order order) throws ServiceException {
        try {
            util.getSession().beginTransaction();
            orderDaoInst.save(order);
            util.getSession().getTransaction().commit();
            logger.info("Save(order):" + order);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls OrderDao getAllClients() method
     *
     * @return <tt>List</tt> of all <tt>Order</tt> objects
     * @throws ServiceException
     */
    @Override
    public List<Order> getAll() throws ServiceException {
        List<Order> ordersList = new ArrayList<>();
        try {
            util.getSession().beginTransaction();
            ordersList = orderDaoInst.getAll();
            util.getSession().getTransaction().commit();
            logger.info("Get All Orders ");
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return ordersList;
    }

    /**
     * Calls OrderDao get() method
     *
     * @param orderId - <tt>Order</tt> object <tt>orderId</tt> property to get the object
     * @return <tt>Order</tt> with <i>orderId</i> id value
     * @throws ServiceException
     */
    @Override
    public Order getById(int orderId) throws ServiceException {
        Order order;
        try {
            util.getSession().beginTransaction();
            order = orderDaoInst.get(Order.class, orderId);
            util.getSession().getTransaction().commit();
            logger.info("GetById(orderId): " + orderId);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return order;
    }

    /**
     * Calls OrderDao getOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @return <tt>List</tt> of <tt>Order</tt> objects with <i>status</i> value
     * @throws ServiceException
     */
    public List<Order> getOrdersListByStatus(OrderStatus status) throws ServiceException {
        List<Order> ordersList = new ArrayList<>();
        try {
            util.getSession().beginTransaction();
            ordersList = orderDaoInst.getOrdersListByStatus(status);
            util.getSession().getTransaction().commit();
            logger.info("getOrdersListByStatus(status): " + status);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return ordersList;
    }

    /**
     * Calls OrderDao getClientOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @param user - determinate the <tt>User</tt> object whose orders are requested
     * @return <tt>List</tt> of <tt>Order</tt> objects of <tt>User</tt> with<i>userId</i> id and <i>status</i> value
     * @throws ServiceException
     */
    public List<Order> getClientOrdersListByStatus(OrderStatus status, User user) throws ServiceException {
        List<Order> ordersList = new ArrayList<>();
        try {
            util.getSession().beginTransaction();
            ordersList = orderDaoInst.getClientOrdersListByStatus(status, user);
            util.getSession().getTransaction().commit();
            logger.info("getClientOrdersListByStatus(status, user):" + status + ", " + user);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return ordersList;
    }

    /**
     * Calls OrderDao checkIsRoomFreeForPeriodInOrder() method
     * @param order - <tt>Order</tt> object, which <tt>room</tt> property is checked
     *              for being free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     *
     * @return true if the <tt>room</tt> is free for the period
     * @throws ServiceException
     */
    public boolean checkIsRoomFreeForPeriodInOrder(Order order) throws ServiceException {
        boolean isFree;
        try {
            util.getSession().beginTransaction();
            isFree = orderDaoInst.isFreeRoomForPeriodInOrder(order);
            util.getSession().getTransaction().commit();
            logger.info("checkIsRoomFreeForPeriodInOrder(order): " + order);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isFree;
    }


    /**
     * Calls OrderDao updateOrderStatus() method
     *
     * @param orderId     - orderId determinate the <tt>Order</tt> object to update <tt>status</tt>
     * @param orderStatus -  value to update <tt>Order</tt> object <tt>status</tt> property
     * @throws ServiceException
     */
    public void updateOrderStatus(int orderId, OrderStatus orderStatus) throws ServiceException {
        try {
            util.getSession().beginTransaction();
            Order order = orderDaoInst.get(Order.class, orderId);
            order.setOrderStatus(orderStatus);
            orderDaoInst.update(order);
            util.getSession().getTransaction().commit();
            logger.info("updateOrderStatus( orderId, orderStatus): " + orderId + ", " + orderStatus);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls OrderDao add() method if OrderDao checkIsRoomFreeForPeriodInOrder() method returns true
     * @param order <tt>Order</tt> object,which will be pushed to DB if it's <tt>room</tt> property
     *              is free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     * @return true if <tt>Order</tt> object's <tt>room</tt> property
     *              is free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     * @throws ServiceException
     */
    public boolean createOrderIfRoomIsFree(Order order)throws ServiceException{
        boolean isFree;
        try {
            util.getSession().beginTransaction();
            util.getSession().load(Room.class, order.getRoom().getRoomId(), LockOptions.UPGRADE);
            isFree = orderDaoInst.isFreeRoomForPeriodInOrder(order);
            if(isFree){
                orderDaoInst.save(order);
            }
            util.getSession().getTransaction().commit();
            logger.info("createOrderIfRoomIsFree(order): " + order);
            logger.info("isFree: "+ isFree);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isFree;
    }

}
