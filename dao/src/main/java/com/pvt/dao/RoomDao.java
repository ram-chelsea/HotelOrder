package com.pvt.dao;


import com.pvt.entities.Room;

import java.sql.Date;
import java.util.List;

public interface RoomDao<T extends Room> extends Dao<T> {
    T getByRoomNumber(String roomNumber);

    boolean isNewRoom(String roomNumber);

    List<T> getSuitedRooms(T orderedRoomFormat, Date checkIn, Date checkOut);//TODO DTO

    List<Integer> getRoominesses();

    int getNumberOfPagesWithRooms(int roomsPerPage);

    List<T> getPageOfRooms(int pageNumber, int roomsPerPage);
}
