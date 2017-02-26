package com.pvtoc.services.impl;


import com.pvtoc.dao.RoomDao;
import com.pvtoc.dto.OrderRoomForm;
import com.pvtoc.entities.Room;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.AbstractEntityService;
import com.pvtoc.services.RoomService;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class RoomServiceImpl extends AbstractEntityService<Room> implements RoomService<Room> {
    private static Logger logger = Logger.getLogger(RoomServiceImpl.class);
    @Autowired
    RoomDao roomDao;

    /**
     * Calls RoomDaoImpl updateRoomPrice() method
     *
     * @param roomId   - roomId determinate the <tt>Room</tt> object to update <tt>price</tt>
     * @param newPrice -  value to update <tt>Room</tt> object <tt>price</tt> property
     * @throws ServiceException
     */
    @Override
    @Transactional
    public void updateRoomPrice(int roomId, int newPrice) throws ServiceException {
        try {
            Room room = ( Room ) roomDao.get(Room.class, roomId);
            room.setPrice(newPrice);
            roomDao.update(room);
            logger.info("UpdateRoomPrice(roomId, newPrice): " + roomId + ", " + newPrice);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls RoomDaoImpl getSuitedRooms() method
     *
     * @param form <tt>OrderRoomForm</tt> to determine properties requested rooms should fit
     * @return <tt>List</tt> of suiting the<i>orderedRoomFormat</i> <tt>Room</tt> objects
     * @throws ServiceException
     */
    @Override
    @Transactional
    public List<Room> getSuitedRooms(OrderRoomForm form) throws ServiceException {
        List<Room> suitedRoomsList = new ArrayList<>();
        try {
            suitedRoomsList = roomDao.getSuitedRooms(form);
            logger.info("GetSuitedRooms(orderedRoomFormat): " + form);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return suitedRoomsList;
    }

    /**
     * Calls RoomDaoImpl getRoominesses() method
     *
     * @return <tt>List</tt> of roominesses values
     * @throws ServiceException
     */
    @Override
    @Transactional
    public List<Integer> getRoominesses() throws ServiceException {
        List<Integer> roominessList = new ArrayList<>();
        try {
            roominessList = roomDao.getRoominesses();
            logger.info("GetRoominesses ");
        } catch (HibernateException e) {
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
    @Override
    @Transactional
    public boolean isNewRoom(Room room) throws ServiceException {
        boolean isNew = false;
        try {
            if (roomDao.isNewRoom(room.getRoomNumber())) {
                isNew = true;
            }
            logger.info("checkIsNewRoom(room): " + room);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isNew;
    }

    /**
     * Calls RoomDaoImpl getNumberOfPagesWithRooms() method
     *
     * @param roomsPerPage - get number of <tt>Room</tt> objects per page
     * @return number of <tt>Room</tt> objects lists
     * @throws ServiceException
     */
    @Override
    @Transactional
    public int getNumberOfPagesWithRooms(int roomsPerPage) throws ServiceException {
        int numberOfPages;
        try {
            numberOfPages = roomDao.getNumberOfPagesWithRooms(roomsPerPage);
            logger.info("getNumberOfPagesWithClients(clientsPerPage): " + roomsPerPage);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return numberOfPages;
    }

    /**
     * Calls RoomDaoImpl getPageOfRooms() method
     *
     * @param pageNumber   - page number of <tt>Room</tt> objects list
     * @param roomsPerPage - number of <tt>Room</tt> objects rooms per page
     * @return <tt>List</tt> of <tt>Room</tt> objects on the <i>pageNumber</i>  with <i>roomsPerPage</i>
     * @throws ServiceException
     */
    @Override
    @Transactional
    public List<Room> getPageOfRooms(int pageNumber, int roomsPerPage) throws ServiceException {
        List<Room> roomsList = new ArrayList<>();
        try {
            roomsList = roomDao.getPageOfRooms(pageNumber, roomsPerPage);
            logger.info("Get Clients For Page Number " + pageNumber);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return roomsList;
    }
    @Override
    public int computeTotalPriceForRoom(Room room, Date from, Date till) {
        return getDatesDifferenceInDays(from, till) * room.getPrice();
    }
}
