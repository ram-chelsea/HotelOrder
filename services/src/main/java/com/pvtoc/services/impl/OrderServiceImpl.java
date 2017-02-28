package com.pvtoc.services.impl;


import com.pvtoc.constants.OrderStatus;
import com.pvtoc.dao.OrderDao;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.AbstractEntityService;
import com.pvtoc.services.OrderService;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@Transactional
public class OrderServiceImpl extends AbstractEntityService<Order> implements OrderService<Order> {
    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
    @Autowired
    OrderDao orderDao;

    /**
     * Calls OrderDaoImpl getOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @return <tt>List</tt> of <tt>Order</tt> objects with <i>status</i> value
     * @throws ServiceException
     */
    @Override
    public List<Order> getOrdersListByStatus(OrderStatus status) throws ServiceException {
        List<Order> ordersList = new ArrayList<>();
        try {
            ordersList = orderDao.getOrdersListByStatus(status);
            logger.info("getOrdersListByStatus(status): " + status);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return ordersList;
    }

    /**
     * Calls OrderDaoImpl getClientOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @param user   - determinate the <tt>User</tt> object whose orders are requested
     * @return <tt>List</tt> of <tt>Order</tt> objects of <tt>User</tt> with<i>userId</i> id and <i>status</i> value
     * @throws ServiceException
     */
    @Override
    public List<Order> getClientOrdersListByStatus(OrderStatus status, User user) throws ServiceException {
        List<Order> ordersList = new ArrayList<>();
        try {
            ordersList = orderDao.getClientOrdersListByStatus(status, user);
            logger.info("getClientOrdersListByStatus(status, user):" + status + ", " + user);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return ordersList;
    }

    /**
     * Calls OrderDaoImpl checkIsRoomFreeForPeriodInOrder() method
     *
     * @param order - <tt>Order</tt> object, which <tt>room</tt> property is checked
     *              for being free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     * @return true if the <tt>room</tt> is free for the period
     * @throws ServiceException
     */
    @Override
    public boolean checkIsRoomFreeForPeriodInOrder(Order order) throws ServiceException {
        boolean isFree;
        try {
            isFree = orderDao.isRoomFreeForPeriodInOrder(order);
            logger.info("checkIsRoomFreeForPeriodInOrder(order): " + order);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isFree;
    }


    /**
     * Calls OrderDaoImpl updateOrderStatus() method
     *
     * @param orderId     - orderId determinate the <tt>Order</tt> object to update <tt>status</tt>
     * @param orderStatus -  value to update <tt>Order</tt> object <tt>status</tt> property
     * @throws ServiceException
     */
    @Override
    public void updateOrderStatus(int orderId, OrderStatus orderStatus) throws ServiceException {
        try {
            Order order = ( Order ) orderDao.get(Order.class, orderId);
            order.setOrderStatus(orderStatus);
            orderDao.update(order);
            logger.info("updateOrderStatus( orderId, orderStatus): " + orderId + ", " + orderStatus);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls OrderDaoImpl add() method if OrderDaoImpl checkIsRoomFreeForPeriodInOrder() method returns true
     *
     * @param order <tt>Order</tt> object,which will be pushed to DB if it's <tt>room</tt> property
     *              is free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     * @return true if <tt>Order</tt> object's <tt>room</tt> property
     * is free for period defined by <tt>checkInDate</tt> and <tt>checkInDate</tt> fields
     * @throws ServiceException
     */
    @Override
    public boolean createOrderIfRoomIsFree(Order order) throws ServiceException {
        boolean isFree;
        try {
            orderDao.getSession().refresh(order.getRoom(), LockOptions.UPGRADE);
            isFree = orderDao.isRoomFreeForPeriodInOrder(order);
            if (isFree) {
                orderDao.save(order);
            }
            logger.info("createOrderIfRoomIsFree(order): " + order);
            logger.info("isFree: " + isFree);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isFree;
    }

}
