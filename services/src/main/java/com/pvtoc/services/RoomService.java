package com.pvtoc.services;


import com.pvtoc.dto.OrderRoomForm;
import com.pvtoc.entities.Room;
import com.pvtoc.exceptions.ServiceException;

import java.sql.Date;
import java.util.List;

public interface RoomService<T extends Room> extends EntityService<T> {
    /**
     * Calls RoomDaoImpl updateRoomPrice() method
     *
     * @param roomId   - roomId determinate the <tt>Room</tt> object to update <tt>price</tt>
     * @param newPrice -  value to update <tt>Room</tt> object <tt>price</tt> property
     * @throws ServiceException
     */
    void updateRoomPrice(int roomId, int newPrice) throws ServiceException;

    /**
     * Calls RoomDaoImpl getSuitedRooms() method
     *
     * @param form <tt>OrderRoomForm</tt> to determine properties requested rooms should fit
     * @return <tt>List</tt> of suiting the<i>orderedRoomFormat</i> <tt>Room</tt> objects
     * @throws ServiceException
     */
    List<T> getSuitedRooms(OrderRoomForm form) throws ServiceException;
    /**
     * Calls RoomDaoImpl getRoominesses() method
     *
     * @return <tt>List</tt> of roominesses values
     * @throws ServiceException
     */
    List<Integer> getRoominesses() throws ServiceException;
    /**
     * Calls RoomDaoImpl isNewRoom() method
     *
     * @param room -  <tt>Room</tt>  to check if it is new
     * @return true if the <tt>Room</tt> is new
     * @throws ServiceException
     */
    boolean isNewRoom(T room) throws ServiceException;
    /**
     * Calls RoomDaoImpl getNumberOfPagesWithRooms() method
     *
     * @param roomsPerPage - get number of <tt>Room</tt> objects per page
     * @return number of <tt>Room</tt> objects lists
     * @throws ServiceException
     */
    int getNumberOfPagesWithRooms(int roomsPerPage) throws ServiceException;
    /**
     * Calls RoomDaoImpl getPageOfRooms() method
     *
     * @param pageNumber   - page number of <tt>Room</tt> objects list
     * @param roomsPerPage - number of <tt>Room</tt> objects rooms per page
     * @return <tt>List</tt> of <tt>Room</tt> objects on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     * @throws ServiceException
     */
    List<T> getPageOfRooms(int pageNumber, int roomsPerPage) throws ServiceException;

    int computeTotalPriceForRoom(T room, Date from, Date till);
}
