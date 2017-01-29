package com.pvt.service.impl;

import com.pvt.constants.RoomClass;
import com.pvt.constants.SqlRequest;
import com.pvt.entities.Room;
import com.pvt.exceptions.ServiceException;
import com.pvt.service.EntityServiceImplTest;
import com.pvt.services.impl.RoomServiceImpl;
import com.pvt.util.EntityBuilder;
import com.pvt.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceImplTest extends EntityServiceImplTest {
 private HibernateUtil util = HibernateUtil.getHibernateUtil();

    @Before
    public void BeforeTest() {
        util.openSession();
    }

    @After
    public void AfterTestCleanDB() {
        Session session = util.getSession();
        Query query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_FALSE);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_ROOMS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_TRUE);
        query.executeUpdate();
        session.close();
    }
    @Test
    public void testGetInstance() {
        RoomServiceImpl firstImpl = RoomServiceImpl.getInstance();
        RoomServiceImpl secondImpl = RoomServiceImpl.getInstance();
        Assert.assertEquals(firstImpl, secondImpl);
    }

    @Test
    public void testAdd() throws ServiceException {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        RoomServiceImpl.getInstance().add(expected);
        Room actual = (Room) util.getSession().get(Room.class, expected.getRoomId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws ServiceException {
        List<Room> roomListExpected = new ArrayList<>();
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        roomListExpected.add(room1);
        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
        roomListExpected.add(room2);
        Room room3 = EntityBuilder.buildRoom(null, "203", 3, RoomClass.DELUXE, 150);
        roomListExpected.add(room3);
        for (Room room : roomListExpected) {
            util.getSession().save(room);
        }
        util.getSession().flush();
        List<Room> roomListActual = RoomServiceImpl.getInstance().getAll();

        Assert.assertTrue(roomListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomListExpected));
    }

    @Test
    public void testGetById() throws ServiceException{
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room falseRoom = EntityBuilder.buildRoom(null, "202", 1, RoomClass.SUITE, 5);
        util.getSession().save(expected);
        util.getSession().save(falseRoom);
        util.getSession().flush();
        Room actual = RoomServiceImpl.getInstance().getById(expected.getRoomId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateNewPrice() throws ServiceException {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        util.getSession().save(expected);
        int expectedPrice = 15;
        util.getSession().flush();
        RoomServiceImpl.getInstance().updateRoomPrice(expected.getRoomId(), expectedPrice);
        Assert.assertEquals(expectedPrice, (int)expected.getPrice());
    }

    @Test
    public void testGetRoominesses() throws ServiceException{
        List<Room> roomList = new ArrayList<>();
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        roomList.add(room1);
        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
        roomList.add(room2);
        Room room3 = EntityBuilder.buildRoom(null, "203", 2, RoomClass.DELUXE, 45);
        roomList.add(room3);
        Room room4 = EntityBuilder.buildRoom(null, "204", 5, RoomClass.SUITE, 5);
        roomList.add(room4);
        Room room5 = EntityBuilder.buildRoom(null, "205", 7, RoomClass.SUITE, 10);
        roomList.add(room5);
        List<Integer> expectedRoominessesList = new ArrayList<>();
        for (Room room : roomList) {
            util.getSession().save(room);
            expectedRoominessesList.add(room.getRoominess());
        }
        util.getSession().flush();
        List<Integer> actualRoominessesList = RoomServiceImpl.getInstance().getRoominesses();
        Assert.assertTrue(expectedRoominessesList.containsAll(actualRoominessesList) && actualRoominessesList.containsAll(expectedRoominessesList));
    }

    @Test
    public void testIsNewRoomTrue() throws ServiceException {
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room room2 = EntityBuilder.buildRoom(null, "202", 1, RoomClass.SUITE, 5);
        util.getSession().save(room1);
        util.getSession().flush();
        boolean isNew = RoomServiceImpl.getInstance().isNewRoom(room2);
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewRoomFalse() throws ServiceException {
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room room2 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        util.getSession().save(room1);
        util.getSession().flush();
        boolean isNew = RoomServiceImpl.getInstance().isNewRoom(room2);
        Assert.assertFalse(isNew);
    }


}
