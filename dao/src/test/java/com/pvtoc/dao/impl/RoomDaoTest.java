package com.pvtoc.dao.impl;

import com.pvtoc.constants.OrderStatus;
import com.pvtoc.constants.RoomClass;
import com.pvtoc.constants.UserRole;
import com.pvtoc.dao.RoomDao;
import com.pvtoc.dto.OrderRoomForm;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.Room;
import com.pvtoc.entities.User;
import com.pvtoc.util.EntityBuilder;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dao-test-app-context.xml")
@Transactional
@Rollback
public class RoomDaoTest{

    @Autowired
    RoomDao<Room> roomDao;

    private Session session;

    @Before
    public void BeforeTest() {
        session = roomDao.getSession();
    }


    @Test
    public void testGetAll() {
        List<Room> roomListExpected = new ArrayList<>();
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        roomListExpected.add(room1);
        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
        roomListExpected.add(room2);
        Room room3 = EntityBuilder.buildRoom(null, "203", 3, RoomClass.DELUXE, 150);
        roomListExpected.add(room3);
        for (Room room : roomListExpected) {
            session.save(room);
        }
        session.flush();
        List<Room> roomListActual = roomDao.getAll();
        Assert.assertTrue(roomListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomListExpected));
    }

    @Test
    public void testSave() {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        roomDao.save(expected);
        session.flush();
        Room actual = (Room) session.get(Room.class, expected.getRoomId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGet() {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room falseRoom = EntityBuilder.buildRoom(null, "202", 1, RoomClass.SUITE, 5);
        session.save(expected);
        session.save(falseRoom);
        session.flush();
        Room actual = roomDao.get(Room.class, expected.getRoomId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testByRoomNumber() {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room falseRoom = EntityBuilder.buildRoom(null, "202", 1, RoomClass.SUITE, 5);
        session.save(expected);
        session.save(falseRoom);
        session.flush();
        Room actual = roomDao.getByRoomNumber(expected.getRoomNumber());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testDelete() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(room);
        int id = room.getRoomId();
        roomDao.delete(room);
        session.flush();
        Room actual = roomDao.get(Room.class, id);
        Assert.assertNull(actual);
    }

    @Test
    public void testIsNewRoomTrue() {
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
        session.save(room1);
        session.flush();
        boolean isNew = roomDao.isNewRoom(room2.getRoomNumber());
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewRoomFalse() {
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room room2 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(room1);
        session.flush();
        boolean isNew = roomDao.isNewRoom(room2.getRoomNumber());
        Assert.assertFalse(isNew);
    }

    @Test
    public void testUpdate() {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(expected);
        int actualPrice = 15;
        expected.setPrice(actualPrice);
        roomDao.update(expected);
        session.flush();
        Assert.assertEquals(actualPrice, (int) expected.getPrice());
    }

    @Test
    public void testGetRoominesses() {
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
            session.save(room);
            expectedRoominessesList.add(room.getRoominess());
        }
        session.flush();
        List<Integer> actualRoominessesList = roomDao.getRoominesses();
        Assert.assertTrue(expectedRoominessesList.containsAll(actualRoominessesList) && actualRoominessesList.containsAll(expectedRoominessesList));
    }

    @Test
    public void testGetPagesOfRooms() {
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(room1);
        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
        session.save(room2);
        Room room3 = EntityBuilder.buildRoom(null, "203", 2, RoomClass.DELUXE, 45);
        session.save(room3);
        Room room4 = EntityBuilder.buildRoom(null, "204", 5, RoomClass.SUITE, 5);
        session.save(room4);
        Room room5 = EntityBuilder.buildRoom(null, "205", 7, RoomClass.SUITE, 10);
        session.save(room5);
        List<Room> roomsListExpected = new ArrayList<>();
        roomsListExpected.add(room3);
        roomsListExpected.add(room4);
        session.flush();
        List<Room> roomListActual = roomDao.getPageOfRooms(2,2);
        Assert.assertTrue(roomsListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomsListExpected));
    }
    @Test
    public void testGetNumberOfPagesWithRooms(){
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(room1);
        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
        session.save(room2);
        Room room3 = EntityBuilder.buildRoom(null, "203", 2, RoomClass.DELUXE, 45);
        session.save(room3);
        Room room4 = EntityBuilder.buildRoom(null, "204", 5, RoomClass.SUITE, 5);
        session.save(room4);
        Room room5 = EntityBuilder.buildRoom(null, "205", 7, RoomClass.SUITE, 10);
        session.save(room5);
        List<Room> roomsListExpected = new ArrayList<>();
        roomsListExpected.add(room3);
        roomsListExpected.add(room4);
        session.flush();
        int roomPagesNumber = roomDao.getNumberOfPagesWithRooms(2);
        Assert.assertEquals(3, roomPagesNumber);
    }

    @Test
    public void testGetSuitedRooms() {
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(room1);
        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
        session.save(room2);
        Room room3 = EntityBuilder.buildRoom(null, "203", 2, RoomClass.STANDART, 55);
        session.save(room3);
        Room room4 = EntityBuilder.buildRoom(null, "204", 2, RoomClass.SUITE, 5);
        session.save(room4);
        Room room5 = EntityBuilder.buildRoom(null, "205", 3, RoomClass.STANDART, 10);
        session.save(room5);
        Room room6 = EntityBuilder.buildRoom(null, "206", 2, RoomClass.STANDART, 45);
        session.save(room6);

        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order = EntityBuilder.buildOrder(null, user, room6, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        session.save(order);
        List<Room> roomsListExpected = new ArrayList<>();
        roomsListExpected.add(room2);
        roomsListExpected.add(room3);
        OrderRoomForm orderRoomForm = new OrderRoomForm(2,RoomClass.STANDART, checkIn,checkOut);
        session.flush();
        List<Room> roomListActual = roomDao.getSuitedRooms(orderRoomForm);
        Assert.assertTrue(roomsListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomsListExpected));
    }

}
