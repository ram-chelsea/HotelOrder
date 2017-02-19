package com.pvt.services;


import com.pvt.entities.Room;
import com.pvt.exceptions.ServiceException;

import java.sql.Date;
import java.util.List;

public interface RoomService<T extends Room> extends EntityService<T> {

    void updateRoomPrice(int roomId, int newPrice) throws ServiceException;

    List<T> getSuitedRooms(T orderedRoomFormat, Date checkInDate, Date checkOutDate) throws ServiceException;//TODO DTO

    List<Integer> getRoominesses() throws ServiceException;

    boolean isNewRoom(T room) throws ServiceException;

    int getNumberOfPagesWithRooms(int roomsPerPage) throws ServiceException;

    List<T> getPageOfRooms(int pageNumber, int roomsPerPage) throws ServiceException;

    int computeTotalPriceForRoom(T room, Date from, Date till);
}
