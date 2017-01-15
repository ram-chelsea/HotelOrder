package com.pvt.services.impl;


import com.pvt.dao.impl.RoomDaoImpl;
import com.pvt.entities.Room;
import com.pvt.exceptions.DaoException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PoolManager;
import com.pvt.services.GeneralService;
import com.pvt.utils.ProjectLogger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl extends GeneralService<Room> {
    /**
     * Singleton object of <tt>RoomServiceImpl</tt> class
     */
    private static RoomServiceImpl instance;

    /**
     * Creates a RoomServiceImpl variable
     */
    private RoomServiceImpl() {
    }

    /**
     * Describes synchronized method of getting <tt>RoomServiceImpl</tt> singleton object
     *
     * @return <tt>RoomServiceImpl</tt> singleton object
     */
    public static synchronized RoomServiceImpl getInstance() {
        if (instance == null) {
            instance = new RoomServiceImpl();
        }
        return instance;
    }
    /**
     * Calls RoomDaoImpl add() method
     *
     * @param room - <tt>Room</tt> object to add
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public void add(Room room) throws SQLException, ServiceException {
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            RoomDaoImpl.getInstance().add(room);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }
    /**
     * Calls RoomDaoImpl getAll() method
     *
     * @return <tt>List</tt> of all <tt>Room</tt> objects
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public List<Room> getAll() throws SQLException, ServiceException {
        List<Room> rooms = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            rooms = RoomDaoImpl.getInstance().getAll();
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return rooms;
    }
    /**
     * Calls RoomDaoImpl getById() method
     *
     * @param roomId - <tt>Room</tt> object <tt>roomId</tt> property to get the object
     * @return <tt>Room</tt> with <i>roomId</i> id value
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public Room getById(int roomId) throws SQLException, ServiceException {
        Room room;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            room = RoomDaoImpl.getInstance().getById(roomId);
            connection.commit();
            ProjectLogger.getInstance().logError(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            connection.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return room;
    }
    /**
     * Calls RoomDaoImpl updateRoomPrice() method
     *
     * @param roomId     - roomId determinate the <tt>Room</tt> object to update <tt>price</tt>
     * @param newPrice -  value to update <tt>Room</tt> object <tt>price</tt> property
     * @throws SQLException
     * @throws ServiceException
     */
    public void updateRoomPrice(int roomId, int newPrice) throws SQLException, ServiceException {
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            RoomDaoImpl.getInstance().updateRoomPrice(roomId, newPrice);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    /**
     * Calls RoomDaoImpl getSuitedRooms() method
     * @param orderedRoomFormat <tt>Room</tt> to determine properties requested rooms should fit
     * @param checkInDate order starting date
     * @param checkOutDate order ending date
     * @return <tt>List</tt> of suiting the<i>orderedRoomFormat</i> <tt>Room</tt> objects
     * @throws SQLException
     * @throws ServiceException
     */
    public List<Room> getSuitedRooms(Room orderedRoomFormat, Date checkInDate, Date checkOutDate) throws SQLException, ServiceException {
        List<Room> suitedRoomsList = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            suitedRoomsList = RoomDaoImpl.getInstance().getSuitedRooms(orderedRoomFormat, checkInDate, checkOutDate);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return suitedRoomsList;
    }

    /**
     * Calls RoomDaoImpl getRoominesses() method
     * @return <tt>List</tt> of roominesses values
     * @throws SQLException
     * @throws ServiceException
     */
    public List<Integer> getRoominesses() throws SQLException, ServiceException {
        List<Integer> roominessList;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            roominessList = RoomDaoImpl.getInstance().getRoominesses();
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return roominessList;
    }
    /**
     * Calls RoomDaoImpl isNewRoom() method
     *
     * @param room -  <tt>Room</tt>  to check if it is new
     * @return true if the <tt>Room</tt> is new
     * @throws SQLException
     * @throws ServiceException
     */
    public boolean isNewRoom(Room room) throws SQLException, ServiceException {
        boolean isNew = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            if ((RoomDaoImpl.getInstance().getById(room.getRoomId()) == null) & (RoomDaoImpl.getInstance().isNewRoom(room.getRoomNumber()))) {
                isNew = true;
            }
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return isNew;
    }

    public int computeTotalPriceForRoom(Room room, Date from, Date till) {
        return getDatesDifferenceInDays(from, till) * room.getPrice();
    }

}
