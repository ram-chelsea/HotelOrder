package com.pvt.dao.impl;

import com.pvt.constants.RoomClass;
import com.pvt.constants.SqlRequest;
import com.pvt.dao.EntityDaoImplTest;
import com.pvt.entities.Room;
import com.pvt.exceptions.DaoException;
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

public class RoomDaoImplTest extends EntityDaoImplTest {
    @Before
    @After
    public void CleanTestDB() throws DaoException, SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_ROOMS);
    }

    @Test
    public void testGetInstance() {
        RoomDaoImpl firstImpl = RoomDaoImpl.getInstance();
        RoomDaoImpl secondImpl = RoomDaoImpl.getInstance();
        Assert.assertEquals(firstImpl.hashCode(), secondImpl.hashCode());
    }

    @Test
    public void testGetAll() throws DaoException {
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
        List<Room> roomListActual = RoomDaoImpl.getInstance().getAll();

        Assert.assertTrue(roomListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomListExpected));
    }

    @Test
    public void testAdd() throws DaoException {
        Room expected = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(expected);
        Room actual = RoomDaoImpl.getInstance().getByRoomNumber(expected.getRoomNumber());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testGetById() throws DaoException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        Room otherRoom = EntityBuilder.buildRoom(2, "202", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        RoomDaoImpl.getInstance().add(otherRoom);
        Room expected = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        Room actual = RoomDaoImpl.getInstance().getById(expected.getRoomId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testByRoomNumber() throws DaoException {
        Room expected = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        Room otherRoom = EntityBuilder.buildRoom(2, "202", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(expected);
        RoomDaoImpl.getInstance().add(otherRoom);
        Room actual = RoomDaoImpl.getInstance().getByRoomNumber(expected.getRoomNumber());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testDelete() throws DaoException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room expected = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        RoomDaoImpl.getInstance().delete(expected.getRoomId());
        Room actual = RoomDaoImpl.getInstance().getById(expected.getRoomId());
        Assert.assertNull(actual);
    }

    @Test
    public void testIsNewRoomTrue() throws DaoException {
        Room room1 = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        Room room2 = EntityBuilder.buildRoom(2, "202", 2, RoomClass.STANDART, 45);
        RoomDaoImpl.getInstance().add(room1);
        boolean isNew = RoomDaoImpl.getInstance().isNewRoom(room2.getRoomNumber());
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewRoomFalse() throws DaoException {
        Room expected = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(expected);
        boolean isNew = RoomDaoImpl.getInstance().isNewRoom(expected.getRoomNumber());
        Assert.assertFalse(isNew);
    }

    @Test
    public void testUpdateNewPrice() throws DaoException, SQLException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room expected = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        int expectedPrice = 15;
        RoomDaoImpl.getInstance().updateRoomPrice(expected.getRoomId(), expectedPrice);
        Room actual = RoomDaoImpl.getInstance().getById(expected.getRoomId());
        Assert.assertEquals(expectedPrice, actual.getPrice());
    }

    @Test
    public void testGetRoominesses() throws DaoException {
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
        List<Integer> actualRoominessesList = RoomDaoImpl.getInstance().getRoominesses();
        Assert.assertTrue(expectedRoominessesList.containsAll(actualRoominessesList) && actualRoominessesList.containsAll(expectedRoominessesList));


    }

}
