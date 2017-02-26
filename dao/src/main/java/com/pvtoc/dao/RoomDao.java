package com.pvtoc.dao;


import com.pvtoc.dto.OrderRoomForm;
import com.pvtoc.entities.Room;

import java.util.List;

/**
 * Describes the interface of DAO layer being used in the projects
 * to implement CRUD operations with <tt>Room</tt> entities
 *
 * @param <T> - <tt>Room</tt>-child class object, being processed in the CRUD operations
 */
public interface RoomDao<T extends Room> extends Dao<T> {
    /**
     * Returns the Object of <tt>Room</tt> class from the database by its <i>roomNumber</i> value
     *
     * @param roomNumber value of <tt>Room</tt> property being used to get the object from the database
     * @return <tt>Room</tt> object, having corresponding <i>roomNumber</i> value
     */
    T getByRoomNumber(String roomNumber);

    /**
     * Checks if the <tt>Room</tt> with the corresponding <i>roomNumber</i> hasn't existed
     * in the database
     *
     * @param roomNumber being used for checking if the <tt>Room</tt> object
     *                   with the corresponding <tt>roomNumber</tt> property value doesn't exist in the database
     * @return true if <tt>Room</tt> with <i>roomNumber</i> doesn't exist in the database
     */
    boolean isNewRoom(String roomNumber);

    /**
     * Return <tt>List</tt> of <tt>Room</tt> objects with properties determined by <i>OrderRoomForm</i>-object which are free to be booked for the period determined by <i>checkIn</i>
     * and <i>checkOut</i> dates
     *
     * @param form describes DTO <tt>OrderRoomForm</tt> object which <tt>roominess</tt> , <tt>roomClass</tt>, <i>checkIn</i>
     *             and <i>checkOut</i> dates properties are requested to order
     * @return <tt>List</tt> of <tt>Room</tt> objects with properties determined by <i>OrderRoomForm</i>-object which are free to be booked for the period determined by <i>checkIn</i>
     * and <i>checkOut</i> dates
     */
    List<T> getSuitedRooms(OrderRoomForm form);

    /**
     * Return <tt>List</tt> of all <tt>roominess</tt> values in the database
     *
     * @return <tt>List</tt> of all <tt>roominess</tt> values in the database
     */
    List<Integer> getRoominesses();

    /**
     * Returns number of pages with rooms in dependence of <i>roomsPerPage</i>
     *
     * @param roomsPerPage number of <tt>Room</tt> objects rooms per page
     * @return number of <tt>Room</tt> objects lists
     */
    int getNumberOfPagesWithRooms(int roomsPerPage);

    /**
     * Returns list of rooms objects on the pageNumber with roomsPerPage
     *
     * @param pageNumber   - page number of <tt>Room</tt> objects list
     * @param roomsPerPage - number of <tt>Room</tt> objects rooms per page
     * @return <tt>List</tt> of <tt>Room</tt> objects on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     */
    List<T> getPageOfRooms(int pageNumber, int roomsPerPage);
}
