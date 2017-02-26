package com.pvtoc.dao.impl;


import com.pvtoc.constants.HqlRequest;
import com.pvtoc.dao.GeneralDao;
import com.pvtoc.dao.RoomDao;
import com.pvtoc.dto.OrderRoomForm;
import com.pvtoc.entities.Room;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child repository class being used for executing
 * of CRUD operations with <tt>Room</tt> object
 */
@Repository
@NoArgsConstructor
public class RoomDaoImpl extends GeneralDao<Room> implements RoomDao<Room> {

    /**
     * Get all <tt>Room</tt> objects being contained in the database
     *
     * @return <tt>List</tt> of all  <tt>Room</tt> objects being contained in the database
     */
    @Override
    public List<Room> getAll() {
        Query query = getSession().createQuery(HqlRequest.GET_ALL_ROOMS);
        List<Room> roomList = query.list();
        return roomList;
    }

    /**
     * Returns the Object of <tt>Room</tt> class from the database by its <i>roomNumber</i> value
     *
     * @param roomNumber value of <tt>Room</tt> property being used to get the object from the database
     * @return <tt>Room</tt> object, having corresponding <i>roomNumber</i> value
     */
    @Override
    public Room getByRoomNumber(String roomNumber) {
        Query query = getSession().createQuery(HqlRequest.GET_ROOM_BY_NUMBER);
        query.setParameter(0, roomNumber);
        Room room = ( Room ) query.uniqueResult();
        return room;
    }

    /**
     * Checks if the <tt>Room</tt> with the corresponding <i>roomNumber</i> hasn't existed
     * in the database
     *
     * @param roomNumber being used for checking if the <tt>Room</tt> object
     *                   with the corresponding <tt>roomNumber</tt> property value doesn't exist in the database
     * @return true if <tt>Room</tt> with <i>roomNumber</i> doesn't exist in the database
     */
    @Override
    public boolean isNewRoom(String roomNumber) {
        Query query = getSession().createQuery(HqlRequest.CHECK_IS_NEW_ROOM);
        query.setParameter(0, roomNumber);
        long count = ( Long ) query.uniqueResult();
        boolean isNewRoom = (count == 0);
        return isNewRoom;
    }

    /**
     * Return <tt>List</tt> of <tt>Room</tt> objects with properties determined by <i>OrderRoomForm</i>-object which are free to be booked for the period determined by <i>checkIn</i>
     * and <i>checkOut</i> dates
     *
     * @param form describes DTO <tt>OrderRoomForm</tt> object which <tt>roominess</tt> , <tt>roomClass</tt>, <i>checkIn</i>
     * and <i>checkOut</i> dates properties are requested to order
     * @return <tt>List</tt> of <tt>Room</tt> objects with properties determined by <i>OrderRoomForm</i>-object which are free to be booked for the period determined by <i>checkIn</i>
     * and <i>checkOut</i> dates
     */
    @Override
    public List<Room> getSuitedRooms(OrderRoomForm form) {
        Query query = getSession().createQuery(HqlRequest.GET_ALL_FREE_ROOMS_FOR_PERIOD_WITH_CATEGORIES);
        query.setParameter(0, form.getRoominess());
        query.setParameter(1, form.getRoomClass());
        query.setParameter(2, form.getCheckIn());
        query.setParameter(3, form.getCheckOut());
        List<Room> suitedRoomsList = query.list();
        return suitedRoomsList;
    }

    /**
     * Return <tt>List</tt> of all <tt>roominess</tt> values in the database
     *
     * @return <tt>List</tt> of all <tt>roominess</tt> values in the database
     */
    @Override
    public List<Integer> getRoominesses() {
        Query query = getSession().createQuery(HqlRequest.GET_ALL_ROOMINESSES);
        List<Integer> roominessList = query.list();
        return roominessList;
    }

    /**
     * Returns number of pages with rooms in dependence of <i>roomsPerPage</i>
     *
     * @param roomsPerPage number of <tt>Room</tt> objects rooms per page
     * @return number of <tt>Room</tt> objects lists
     */
    @Override
    public int getNumberOfPagesWithRooms(int roomsPerPage) {
        Query query = getSession().createQuery(HqlRequest.GET_ALL_ROOMS_NUMBER);
        int numberOfClients = (( Long ) query.uniqueResult()).intValue();
        int numberOfPages = (numberOfClients - 1) / roomsPerPage + 1;
        return numberOfPages;
    }

    /**
     * Returns list of rooms objects on the pageNumber with roomsPerPage
     *
     * @param pageNumber   - page number of <tt>Room</tt> objects list
     * @param roomsPerPage - number of <tt>Room</tt> objects rooms per page
     * @return <tt>List</tt> of <tt>Room</tt> objects on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     */
    @Override
    public List<Room> getPageOfRooms(int pageNumber, int roomsPerPage) {
        int currentIndex = (pageNumber - 1) * roomsPerPage;
        Query query = getSession().createQuery(HqlRequest.GET_ALL_ROOMS)
                .setFirstResult(currentIndex)
                .setMaxResults(roomsPerPage);
        List<Room> roomsList = query.list();
        return roomsList;
    }
}
