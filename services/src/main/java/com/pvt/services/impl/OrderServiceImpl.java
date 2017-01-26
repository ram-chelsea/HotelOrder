package com.pvt.services.impl;


import com.pvt.constants.OrderStatus;
import com.pvt.dao.impl.OrderDaoImpl;
import com.pvt.entities.Order;
import com.pvt.exceptions.DaoException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PoolManager;
import com.pvt.services.GeneralService;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl extends GeneralService<Order> {
    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
    /**
     * Singleton object of <tt>OrderServiceImpl</tt> class
     */
    private static OrderServiceImpl instance;
    private static OrderDaoImpl orderDaoInst = OrderDaoImpl.getInstance();

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
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public void add(Order order) throws ServiceException, SQLException {
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            orderDaoInst.save(order);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            logger.error(transactionFailedMessage);
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.releaseConnection(connection);
        }
    }

    /**
     * Calls OrderDaoImpl getAll() method
     *
     * @return <tt>List</tt> of all <tt>Order</tt> objects
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public List<Order> getAll() throws SQLException, ServiceException {
        List<Order> ordersList;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            ordersList = orderDaoInst.getAll();
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            logger.error(transactionFailedMessage);
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.releaseConnection(connection);
        }
        return ordersList;
    }

    /**
     * Calls OrderDaoImpl getById() method
     *
     * @param orderId - <tt>Order</tt> object <tt>orderId</tt> property to get the object
     * @return <tt>Order</tt> with <i>orderId</i> id value
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public Order getById(int orderId) throws SQLException, ServiceException {
        Order order;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            order = orderDaoInst.getById(orderId);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            logger.error(transactionFailedMessage);
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.releaseConnection(connection);
        }
        return order;
    }

    /**
     * Calls OrderDaoImpl getOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @return <tt>List</tt> of <tt>Order</tt> objects with <i>status</i> value
     * @throws SQLException
     * @throws ServiceException
     */
    public List<Order> getOrdersListByStatus(OrderStatus status) throws SQLException, ServiceException {
        List<Order> ordersList;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            ordersList = orderDaoInst.getOrdersListByStatus(status);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            logger.error(transactionFailedMessage);
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.releaseConnection(connection);
        }
        return ordersList;
    }

    /**
     * Calls OrderDaoImpl getClientOrdersListByStatus() method
     *
     * @param status - <tt>Order</tt> object <tt>status</tt> property to get the objects <tt>List</tt>
     * @param userId - userId determinate the <tt>User</tt> object whose orders are requested
     * @return <tt>List</tt> of <tt>Order</tt> objects of <tt>User</tt> with<i>userId</i> id and <i>status</i> value
     * @throws SQLException
     * @throws ServiceException
     */
    public List<Order> getClientOrdersListByStatus(OrderStatus status, int userId) throws SQLException, ServiceException {
        List<Order> ordersList;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            ordersList = orderDaoInst.getClientOrdersListByStatus(status, userId);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            logger.error(transactionFailedMessage);
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.releaseConnection(connection);
        }
        return ordersList;
    }

    /**
     * Calls OrderDaoImpl updateOrderStatus() method
     *
     * @param orderId     - orderId determinate the <tt>Order</tt> object to update <tt>status</tt>
     * @param orderStatus -  value to update <tt>Order</tt> object <tt>status</tt> property
     * @throws SQLException
     * @throws ServiceException
     */
    public void updateOrderStatus(int orderId, OrderStatus orderStatus) throws SQLException, ServiceException {
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            orderDaoInst.updateOrderStatus(orderId, orderStatus);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            logger.error(transactionFailedMessage);
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.releaseConnection(connection);
        }
    }

}
