package com.pvt.dao.impl;

import com.pvt.constants.ColumnName;
import com.pvt.constants.OrderStatus;
import com.pvt.constants.SqlRequest;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.DaoException;
import com.pvt.managers.PoolManager;
import com.pvt.util.EntityBuilder;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>Order</tt> object
 */
public class OrderDaoImpl extends GeneralDao<Order> {
    private static Logger logger = Logger.getLogger(OrderDaoImpl.class);
    /**
     * String property being used for describing the error in case of SQL Exception for <i>Log4j</i>
     */
    private static String message;
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
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public void add(Order order) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.ADD_ORDER);
            User user = order.getUser();
            statement.setInt(1, user.getUserId());
            Room room = order.getRoom();
            statement.setInt(2, room.getRoomId());
            statement.setDate(3, order.getCheckInDate());
            statement.setDate(4, order.getCheckOutDate());
            statement.setString(5, order.getOrderStatus().toString());
            statement.setInt(6, order.getTotalPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to add the order ";
            logger.error(message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Get all <tt>Order</tt> objects being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>Order</tt> objects being contained in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public List<Order> getAll() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ALL_ORDERS);
            result = statement.executeQuery();
            while (result.next()) {
                Order order = buildOrder(result);
                orderList.add(order);
            }
        } catch (SQLException e) {
            message = "Unable to return list of orders ";
            logger.error(message);
            throw new DaoException(message, e);
        }
        return orderList;
    }

    /**
     * Returns the Object of <tt>Order</tt> class from the database by its <i>orderId</i> value
     *
     * @param orderId value of <tt>Order</tt> property being used to get the object from the database
     * @return <tt>Order</tt> object, having corresponding <i>orderId</i> value
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public Order getById(int orderId) throws DaoException {
        Order order = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ORDER_BY_ID);
            statement.setInt(1, orderId);
            result = statement.executeQuery();
            while (result.next()) {
                order = buildOrder(result);
            }
        } catch (SQLException e) {
            message = "Unable to return the order ";
            logger.error(message);
            throw new DaoException(message, e);
        }
        return order;
    }

    /**
     * Delete the Object of <tt>Order</tt> class from the database by <i>orderId</i> value
     *
     * @param orderId value of <tt>Order</tt> property being used to delete the corresponding object from the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public void delete(int orderId) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.DELETE_ORDER_BY_ID);
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to delete the order ";
            logger.error(message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Get all <tt>Order</tt> objects with <i>status</i> value being contained in the database
     *
     * @param status describes value of <tt>status</tt> property being used to determinate <tt>Order</tt> objects list
     * @return <tt>List</tt> of all  <tt>Order</tt> objects with <i>status</i> value being contained in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public List<Order> getOrdersListByStatus(OrderStatus status) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ORDERS_BY_STATUS);
            statement.setString(1, status.toString());
            result = statement.executeQuery();
            while (result.next()) {
                Order order = buildOrder(result);
                orderList.add(order);
            }
        } catch (SQLException e) {
            message = "Unable to return list of orders with this status ";
            logger.error(message);
            throw new DaoException(message, e);
        }
        return orderList;
    }

    /**
     * Return all <tt>Order</tt> objects of <tt>User</tt> with <i>userId</i> property value with <i>status</i> value being contained in the database
     *
     * @param status describes value of <tt>status</tt> property being used to determinate <tt>Order</tt> objects list of <tt>User</tt> with <i>userId</i> property value
     * @param userId determinates <tt>User</tt> object, whose <i>status</i> value orders are got
     * @return all <tt>Order</tt> objects of <tt>User</tt> with <i>userId</i> property value with <i>status</i> value being contained in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public List<Order> getClientOrdersListByStatus(OrderStatus status, int userId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_CLIENTS_ORDERS_BY_STATUS);
            statement.setString(1, status.toString());
            statement.setInt(2, userId);
            result = statement.executeQuery();
            while (result.next()) {
                Order order = buildOrder(result);
                orderList.add(order);
            }
        } catch (SQLException e) {
            message = "Unable to return this user's list of orders with this status ";
            logger.error(message);
            throw new DaoException(message, e);
        }
        return orderList;
    }

    public List<Order> getByUserId() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ORDERS_BY_USER_ID);
            result = statement.executeQuery();
            while (result.next()) {
                Order order = buildOrder(result);
                orderList.add(order);
            }
        } catch (SQLException e) {
            message = "Unable to return this user's list of orders ";
            logger.error(message);
            throw new DaoException(message, e);
        }
        return orderList;
    }

    /**
     * Update value of <tt>status</tt> property value of <tt>Order</tt> with <i>orderId</i> property value
     * to <i>orderStatus</i> value
     *
     * @param orderId     determinate <tt>Order</tt> object, whose <i>status</i> value is updated
     * @param orderStatus determines value of <tt>status</tt> property to what <tt>Order</tt> object <tt>status</tt> property value is changed
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public void updateOrderStatus(int orderId, OrderStatus orderStatus) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.UPDATE_ORDER_STATUS);
            statement.setString(1, orderStatus.toString());
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to update status of this order ";
            logger.error(message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Build <tt>Order</tt> object with the properties values corresponding
     * to ResultSet being got after SQL Request executing
     *
     * @param result <tt>ResultSet</tt> being got after SQL Request executing
     * @return <tt>Order</tt> object with the properties values corresponding
     * to ResultSet being got after SQL Request executing
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    private Order buildOrder(ResultSet result) throws SQLException, DaoException {
        int orderId = result.getInt(ColumnName.ORDER_ID);
        int roomId = result.getInt(ColumnName.ORDER_ROOM_ID);
        int userId = result.getInt(ColumnName.ORDER_USER_ID);
        User user = UserDaoImpl.getInstance().getById(userId);
        Room room = RoomDaoImpl.getInstance().getById(roomId);
        Date checkInDate = result.getDate(ColumnName.ORDER_CHECK_IN_DATE);
        Date checkOutDate = result.getDate(ColumnName.ORDER_CHECK_OUT_DATE);
        OrderStatus orderStatus = OrderStatus.valueOf(result.getString(ColumnName.ORDER_STATUS));
        int totalPrice = result.getInt(ColumnName.ORDER_TOTAL_PRICE);

        return EntityBuilder.buildOrder(orderId, user, room, checkInDate, checkOutDate, orderStatus, totalPrice);
    }

}
