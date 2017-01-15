package com.pvt.dao.impl;


import com.pvt.constants.ColumnName;
import com.pvt.constants.RoomClass;
import com.pvt.constants.SqlRequest;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.Room;
import com.pvt.exceptions.DaoException;
import com.pvt.managers.PoolManager;
import com.pvt.util.EntityBuilder;
import com.pvt.utils.ProjectLogger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>Room</tt> object
 */
public class RoomDaoImpl extends GeneralDao<Room> {
    /**
     * String property being used for describing the error in case of SQL Exception for <i>Log4j</i>
     */
    private static String message;
    /**
     * Singleton object of <tt>RoomDaoImpl</tt> class
     */
    private static RoomDaoImpl instance;

    /**
     * Creates a RoomDaoImpl variable
     */
    private RoomDaoImpl() {
    }

    /**
     * Describes synchronized method of getting <tt>RoomDaoImpl</tt> singleton object
     *
     * @return <tt>RoomDaoImpl</tt> singleton object
     */
    public static synchronized RoomDaoImpl getInstance() {
        if (instance == null) {
            instance = new RoomDaoImpl();
        }
        return instance;
    }

    /**
     * Get all <tt>Room</tt> objects being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>Room</tt> objects being contained in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public List<Room> getAll() throws DaoException {
        List<Room> roomList = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ALL_ROOMS);
            result = statement.executeQuery();
            while (result.next()) {
                Room room = buildRoom(result);
                roomList.add(room);
            }
        } catch (SQLException e) {
            message = "Unable to return list of rooms ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return roomList;
    }

    /**
     * Adds the <tt>Room</tt> object properties values into database
     *
     * @param room <tt>Room</tt> element, which properties will be pushed into the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public void add(Room room) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.ADD_ROOM);
            statement.setString(1, room.getRoomNumber());
            statement.setInt(2, room.getRoominess());
            statement.setString(3, room.getRoomClass().toString());
            statement.setInt(4, room.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to add the room ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Returns the Object of <tt>Room</tt> class from the database by its <i>roomId</i> value
     *
     * @param roomId value of <tt>Room</tt> property being used to get the object from the database
     * @return <tt>Room</tt> object, having corresponding <i>roomId</i> value
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public Room getById(int roomId) throws DaoException {
        Room room = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ROOM_BY_ID);
            statement.setInt(1, roomId);
            result = statement.executeQuery();
            while (result.next()) {
                room = buildRoom(result);
            }
        } catch (SQLException e) {
            message = "Unable to return the room ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return room;
    }

    /**
     * Returns the Object of <tt>Room</tt> class from the database by its <i>roomNumber</i> value
     *
     * @param roomNumber value of <tt>Room</tt> property being used to get the object from the database
     * @return <tt>Room</tt> object, having corresponding <i>roomNumber</i> value
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public Room getByRoomNumber(String roomNumber) throws DaoException {
        Room room = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ROOM_BY_NUMBER);
            statement.setString(1, roomNumber);
            result = statement.executeQuery();
            while (result.next()) {
                room = buildRoom(result);
            }
        } catch (SQLException e) {
            message = "Unable to return the room ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return room;
    }

    /**
     * Delete the Object of <tt>Room</tt> class from the database by <i>roomId</i> value
     *
     * @param roomId value of <tt>Room</tt> property being used to delete the corresponding object from the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public void delete(int roomId) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.DELETE_ROOM_BY_ID);
            statement.setInt(1, roomId);
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to delete the room ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Checks if the <tt>Room</tt> with the corresponding <i>roomNumber</i> hasn't existed
     * in the database
     *
     * @param roomNumber being used for checking if the <tt>Room</tt> object
     *                   with the corresponding <tt>roomNumber</tt> property value doesn't exist in the database
     * @return true if <tt>Room</tt> with <i>roomNumber</i> doesn't exist in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public boolean isNewRoom(String roomNumber) throws DaoException {
        boolean isNew = true;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ROOM_BY_NUMBER);
            statement.setString(1, roomNumber);
            result = statement.executeQuery();
            if (result.next()) {
                isNew = false;
            }
        } catch (SQLException e) {
            message = "Unable to check the card ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return isNew;
    }

    /**
     * Update value of <tt>price</tt> property value of <tt>Room</tt> with <i>roomId</i> property value
     * to <i>newPrice</i> value
     *
     * @param roomId   determinates <tt>Room</tt> object, whose <i>price</i> value is updated
     * @param newPrice determines value of <tt>price</tt> property to what <tt>Room</tt> object <tt>price</tt> property value is changed
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public void updateRoomPrice(int roomId, int newPrice) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.UPDATE_ROOM_PRICE);
            statement.setInt(1, newPrice);
            statement.setInt(2, roomId);
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to update the room price ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Return <tt>List</tt> of <tt>Room</tt> objects with properties determined by <i>orderedRoomFormat</i> which are free to be booked for the period determined by <i>checkIn</i>
     * and <i>checkOut</i> dates
     *
     * @param orderedRoomFormat describes pattern <tt>Room</tt> object which <tt>roominess</tt> and <tt>roomClass</tt> properties are requested to order
     * @param checkIn           determinate date for <tt>Order</tt> beginning date
     * @param checkOut          determinate date for <tt>Order</tt> ending date
     * @return <tt>List</tt> of <tt>Room</tt> objects with properties determined by <i>orderedRoomFormat</i> which are free to be booked for the period determined by <i>checkIn</i>
     * and <i>checkOut</i> dates
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public List<Room> getSuitedRooms(Room orderedRoomFormat, Date checkIn, Date checkOut) throws DaoException {
        List<Room> suitedRoomsList = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ALL_FREE_ROOMS_FOR_PERIOD_WITH_CATEGORIES);
            statement.setInt(1, orderedRoomFormat.getRoominess());
            statement.setString(2, orderedRoomFormat.getRoomClass().toString());
            statement.setDate(3, checkIn);
            statement.setDate(4, checkOut);
            result = statement.executeQuery();
            while (result.next()) {
                Room room = buildRoom(result);
                suitedRoomsList.add(room);
            }
        } catch (SQLException e) {
            message = "Unable to get suited rooms list ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return suitedRoomsList;
    }

    /**
     * Return <tt>List</tt> of all <tt>roominess</tt> values in the database
     *
     * @return <tt>List</tt> of all <tt>roominess</tt> values in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public List<Integer> getRoominesses() throws DaoException {
        List<Integer> roominessList = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ALL_ROOMINESSES);
            result = statement.executeQuery();
            while (result.next()) {
                Integer roominess = result.getInt(ColumnName.ROOMINESS);
                roominessList.add(roominess);
            }
        } catch (SQLException e) {
            message = "Unable to return list of roominesses ";
            ProjectLogger.getInstance().logError(getClass(), message);
            throw new DaoException(message, e);
        }
        return roominessList;
    }

    /**
     * Build <tt>Room</tt> object with the properties values corresponding
     * to ResultSet being got after SQL Request executing
     *
     * @param result <tt>ResultSet</tt> being got after SQL Request executing
     * @return <tt>Room</tt> object with the properties values corresponding
     * to ResultSet being got after SQL Request executing
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    private Room buildRoom(ResultSet result) throws SQLException {
        int roomId = result.getInt(ColumnName.ROOM_ID);
        String roomNumber = result.getString(ColumnName.ROOM_NUMBER);
        int roominess = result.getInt(ColumnName.ROOMINESS);
        RoomClass roomClass = RoomClass.valueOf(result.getString(ColumnName.ROOM_CLASS));
        int price = result.getInt(ColumnName.ROOM_PRICE);
        return EntityBuilder.buildRoom(roomId, roomNumber, roominess, roomClass, price);
    }
}
