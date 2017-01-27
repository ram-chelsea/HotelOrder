package com.pvt.service.impl;

import com.pvt.constants.RoomClass;
import com.pvt.constants.SqlRequest;
import com.pvt.dao.impl.RoomDaoImpl;
import com.pvt.entities.Room;
import com.pvt.exceptions.DaoException;
import com.pvt.exceptions.ServiceException;
import com.pvt.service.EntityServiceImplTest;
import com.pvt.services.impl.RoomServiceImpl;
import com.pvt.util.EntityBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceImplTest extends EntityServiceImplTest {

    @Before
    @After
    public void CleanTestDB() throws DaoException, SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_ROOMS);

    }

    @Test
    public void testGetInstance() {
        RoomServiceImpl firstImpl = RoomServiceImpl.getInstance();
        RoomServiceImpl secondImpl = RoomServiceImpl.getInstance();
        Assert.assertEquals(firstImpl.hashCode(), secondImpl.hashCode());
    }

    @Test
    public void testAdd() throws DaoException, SQLException, ServiceException {
        Room expected = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomServiceImpl.getInstance().add(expected);
        Room actual = RoomDaoImpl.getInstance().getByRoomNumber(expected.getRoomNumber());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testGetAll() throws DaoException, SQLException, ServiceException {
        List<Room> roomListExpected = new ArrayList<>();
        Room room1 = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        roomListExpected.add(room1);
        Room room2 = EntityBuilder.buildRoom(2, "202", 2, RoomClass.STANDART, 45);
        roomListExpected.add(room2);
        Room room3 = EntityBuilder.buildRoom(3, "203", 3, RoomClass.DELUXE, 150);
        roomListExpected.add(room3);
        for (Room room : roomListExpected) {
            RoomDaoImpl.getInstance().add(room);
        }
        List<Room> roomListActual = RoomServiceImpl.getInstance().getAll();

        Assert.assertTrue(roomListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomListExpected));
    }

    @Test
    public void testGetById() throws DaoException, ServiceException, SQLException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        Room otherRoom = EntityBuilder.buildRoom(2, "202", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        RoomDaoImpl.getInstance().add(otherRoom);
        Room expected = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        Room actual = RoomServiceImpl.getInstance().getById(expected.getRoomId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateNewPrice() throws DaoException, SQLException, ServiceException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room expected = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        int expectedPrice = 15;
        RoomServiceImpl.getInstance().updateRoomPrice(expected.getRoomId(), expectedPrice);
        Room actual = RoomDaoImpl.getInstance().getById(expected.getRoomId());
        Assert.assertEquals(expectedPrice, actual.getPrice());
    }

    @Test
    public void testGetRoominesses() throws DaoException, ServiceException, SQLException {
        List<Room> roomList = new ArrayList<>();
        Room room1 = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        roomList.add(room1);
        Room room2 = EntityBuilder.buildRoom(2, "202", 2, RoomClass.STANDART, 45);
        roomList.add(room2);
        Room room3 = EntityBuilder.buildRoom(3, "203", 3, RoomClass.DELUXE, 45);
        roomList.add(room3);
        Room room4 = EntityBuilder.buildRoom(4, "204", 5, RoomClass.SUITE, 5);
        roomList.add(room4);
        Room room5 = EntityBuilder.buildRoom(5, "205", 7, RoomClass.SUITE, 10);
        roomList.add(room5);
        List<Integer> expectedRoominessesList = new ArrayList<>();
        for (Room room : roomList) {
            RoomDaoImpl.getInstance().add(room);
            expectedRoominessesList.add(room.getRoominess());
        }
        List<Integer> actualRoominessesList = RoomServiceImpl.getInstance().getRoominesses();
        Assert.assertTrue(expectedRoominessesList.containsAll(actualRoominessesList) && actualRoominessesList.containsAll(expectedRoominessesList));
    }

    @Test
    public void testIsNewRoomTrue() throws DaoException, SQLException, ServiceException {
        Room room1 = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        Room preRoom = EntityBuilder.buildRoom(2, "202", 2, RoomClass.STANDART, 45);
        RoomDaoImpl.getInstance().add(room1);
        RoomDaoImpl.getInstance().add(preRoom);
        Room room2 = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        RoomDaoImpl.getInstance().delete(room2.getRoomId());
        boolean isNew = RoomServiceImpl.getInstance().isNewRoom(room2);
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewRoomFalse() throws DaoException, SQLException, ServiceException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room expected = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        boolean isNew = RoomServiceImpl.getInstance().isNewRoom(expected);
        Assert.assertFalse(isNew);
    }


}
