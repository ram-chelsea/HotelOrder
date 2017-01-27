package com.pvt.services.impl;


import com.pvt.dao.impl.RoomDaoImpl;
import com.pvt.entities.Room;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.GeneralService;
import com.pvt.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.List;

public class RoomServiceImpl extends GeneralService<Room> {
    private static Logger logger = Logger.getLogger(RoomServiceImpl.class);
    /**
     * Singleton object of <tt>RoomServiceImpl</tt> class
     */
    private static RoomServiceImpl instance;
    private static RoomDaoImpl roomDaoInst = RoomDaoImpl.getInstance();
    private Transaction transaction;
    public static HibernateUtil util = HibernateUtil.getHibernateUtil();

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
     * @throws ServiceException
     */
    @Override
    public void add(Room room) throws ServiceException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            roomDaoInst.save(room);
            transaction.commit();
            logger.info("Save(room):" + room);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls RoomDaoImpl getAll() method
     *
     * @return <tt>List</tt> of all <tt>Room</tt> objects
     * @throws ServiceException
     */
    @Override
    public List<Room> getAll() throws ServiceException {
        List<Room> rooms;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            rooms = roomDaoInst.getAll();
            transaction.commit();
            logger.info("Get All Rooms");
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return rooms;
    }//TODO разобраться с NPE. List<Room> rooms = new ArrayList<>(); или List<Room> rooms;

    /**
     * Calls RoomDaoImpl getById() method
     *
     * @param roomId - <tt>Room</tt> object <tt>roomId</tt> property to get the object
     * @return <tt>Room</tt> with <i>roomId</i> id value
     * @throws ServiceException
     */
    @Override
    public Room getById(int roomId) throws ServiceException {
        Room room;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            room = roomDaoInst.getById(roomId);
            transaction.commit();
            logger.info("GetById(roomId): " + roomId);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return room;
    }

    /**
     * Calls RoomDaoImpl updateRoomPrice() method
     *
     * @param roomId   - roomId determinate the <tt>Room</tt> object to update <tt>price</tt>
     * @param newPrice -  value to update <tt>Room</tt> object <tt>price</tt> property
     * @throws ServiceException
     */
    public void updateRoomPrice(int roomId, int newPrice) throws ServiceException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            roomDaoInst.updateRoomPrice(roomId, newPrice);
            transaction.commit();
            logger.info("UpdateRoomPrice(roomId, newPrice): " + roomId + ", " + newPrice);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls RoomDaoImpl getSuitedRooms() method
     *
     * @param orderedRoomFormat <tt>Room</tt> to determine properties requested rooms should fit
     * @param checkInDate       order starting date
     * @param checkOutDate      order ending date
     * @return <tt>List</tt> of suiting the<i>orderedRoomFormat</i> <tt>Room</tt> objects
     * @throws ServiceException
     */
    public List<Room> getSuitedRooms(Room orderedRoomFormat, Date checkInDate, Date checkOutDate) throws ServiceException {
        List<Room> suitedRoomsList;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            suitedRoomsList = roomDaoInst.getSuitedRooms(orderedRoomFormat, checkInDate, checkOutDate);
            transaction.commit();
            logger.info("GetSuitedRooms(orderedRoomFormat, checkInDate, checkOutDate): " + orderedRoomFormat + ", " + checkInDate + ", " + checkOutDate);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return suitedRoomsList;
    }//TODO DTO RoomFormat

    /**
     * Calls RoomDaoImpl getRoominesses() method
     *
     * @return <tt>List</tt> of roominesses values
     * @throws ServiceException
     */
    public List<Integer> getRoominesses() throws ServiceException {
        List<Integer> roominessList;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            roominessList = roomDaoInst.getRoominesses();
            transaction.commit();
            logger.info("GetRoominesses ");
        }catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return roominessList;
    }

    /**
     * Calls RoomDaoImpl isNewRoom() method
     *
     * @param room -  <tt>Room</tt>  to check if it is new
     * @return true if the <tt>Room</tt> is new
     * @throws ServiceException
     */
    public boolean isNewRoom(Room room) throws ServiceException {
        boolean isNew = false;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            if ((roomDaoInst.getById(room.getRoomId()) == null) & (roomDaoInst.isNewRoom(room.getRoomNumber()))) {
                isNew = true;
            }
            transaction.commit();
            logger.info("checkIsNewRoom(room): " + room);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isNew;
    }

    public int computeTotalPriceForRoom(Room room, Date from, Date till) {
        return getDatesDifferenceInDays(from, till) * room.getPrice();
    }

}
