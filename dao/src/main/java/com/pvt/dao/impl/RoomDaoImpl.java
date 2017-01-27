package com.pvt.dao.impl;


import com.pvt.constants.HqlRequest;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.Room;
import com.pvt.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Date;
import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>Room</tt> object
 */
public class RoomDaoImpl extends GeneralDao<Room> {

    private static RoomDaoImpl instance;
    private static HibernateUtil util = HibernateUtil.getHibernateUtil();

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
     */

    public List<Room> getAll() {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_ALL_ROOMS);
        List<Room> roomList = query.list();
        return roomList;
    }

    /**
     * Adds the <tt>Room</tt> object properties values into database
     *
     * @param room <tt>Room</tt> element, which properties will be pushed into the database
     */

    public void save(Room room) {
        Session session = util.getSession();
        session.save(room);
    }

    /**
     * Returns the Object of <tt>Room</tt> class from the database by its <i>roomId</i> value
     *
     * @param roomId value of <tt>Room</tt> property being used to get the object from the database
     * @return <tt>Room</tt> object, having corresponding <i>roomId</i> value
     */

    public Room getById(int roomId) {
        Session session = util.getSession();
        Room room = (Room) session.get(Room.class, roomId);
        return room;
    }

    /**
     * Returns the Object of <tt>Room</tt> class from the database by its <i>roomNumber</i> value
     *
     * @param roomNumber value of <tt>Room</tt> property being used to get the object from the database
     * @return <tt>Room</tt> object, having corresponding <i>roomNumber</i> value
     */
    public Room getByRoomNumber(String roomNumber) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_ROOM_BY_NUMBER);
        query.setParameter(0, roomNumber);
        Room room = (Room) query.uniqueResult();
        return room;
    }

    /**
     * Delete the Object of <tt>Room</tt> class from the database by <i>roomId</i> value
     *
     * @param roomId value of <tt>Room</tt> property being used to delete the corresponding object from the database
     */
    public void delete(int roomId) {
        Session session = util.getSession();
        Room room = (Room) session.get(Room.class, roomId);
        session.delete(room);
    }

    /**
     * Checks if the <tt>Room</tt> with the corresponding <i>roomNumber</i> hasn't existed
     * in the database
     *
     * @param roomNumber being used for checking if the <tt>Room</tt> object
     *                   with the corresponding <tt>roomNumber</tt> property value doesn't exist in the database
     * @return true if <tt>Room</tt> with <i>roomNumber</i> doesn't exist in the database
     */
    public boolean isNewRoom(String roomNumber){
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.CHECK_IS_NEW_ROOM);
        query.setParameter(0, roomNumber);
        int count = ((Long) query.uniqueResult()).intValue();
        boolean isNewRoom = (count == 0);
        return isNewRoom;
    }

    /**
     * Update value of <tt>price</tt> property value of <tt>Room</tt> with <i>roomId</i> property value
     * to <i>newPrice</i> value
     *
     * @param roomId   determinates <tt>Room</tt> object, whose <i>price</i> value is updated
     * @param newPrice determines value of <tt>price</tt> property to what <tt>Room</tt> object <tt>price</tt> property value is changed
     */
    public void updateRoomPrice(int roomId, int newPrice){
        Session session = util.getSession();
        Room room = (Room) session.get(Room.class, roomId);
        room.setPrice(newPrice);
        session.update(room);
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
     */
    public List<Room> getSuitedRooms(Room orderedRoomFormat, Date checkIn, Date checkOut) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_ALL_FREE_ROOMS_FOR_PERIOD_WITH_CATEGORIES);
        query.setParameter(0, orderedRoomFormat.getRoominess());
        query.setParameter(1, orderedRoomFormat.getRoomClass());
        query.setParameter(2, checkIn);
        query.setParameter(3, checkOut);
        List<Room> suitedRoomsList = query.list();
        return suitedRoomsList;
    }

    /**
     * Return <tt>List</tt> of all <tt>roominess</tt> values in the database
     *
     * @return <tt>List</tt> of all <tt>roominess</tt> values in the database
     */
    public List<Integer> getRoominesses() {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_ALL_ROOMINESSES);
        List<Integer> roominessList = query.list();
        return roominessList;
    }

}
