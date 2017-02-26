package com.pvtoc.service.impl;

import com.pvtoc.constants.OrderStatus;
import com.pvtoc.constants.RoomClass;
import com.pvtoc.constants.UserRole;
import com.pvtoc.dto.OrderRoomForm;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.Room;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.RoomService;
import com.pvtoc.util.EntityBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
@ContextConfiguration("/service-test-app-context.xml")
@Transactional
@Rollback
public class RoomServiceImplTest {
    @Autowired
    RoomService<Room> roomService;
    @Autowired
    SessionFactory sessionFactory;

    private Session session;

    @Before
    public void BeforeTest() {
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void testAdd() throws ServiceException {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        roomService.add(expected);
        Room actual = ( Room ) session.get(Room.class, expected.getRoomId());
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
            session.save(room);
        }
        session.flush();
        List<Room> roomListActual = roomService.getAll();

        Assert.assertTrue(roomListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomListExpected));
    }

    @Test
    public void testGetById() throws ServiceException {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room falseRoom = EntityBuilder.buildRoom(null, "202", 1, RoomClass.SUITE, 5);
        session.save(expected);
        session.save(falseRoom);
        session.flush();
        Room actual = roomService.get(Room.class, expected.getRoomId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateNewPrice() throws ServiceException {
        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(expected);
        int expectedPrice = 15;
        session.flush();
        roomService.updateRoomPrice(expected.getRoomId(), expectedPrice);
        Assert.assertEquals(expectedPrice, ( int ) expected.getPrice());
    }

    @Test
    public void testGetRoominesses() throws ServiceException {
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
        List<Integer> actualRoominessesList = roomService.getRoominesses();
        Assert.assertTrue(expectedRoominessesList.containsAll(actualRoominessesList) && actualRoominessesList.containsAll(expectedRoominessesList));
    }

    @Test
    public void testIsNewRoomTrue() throws ServiceException {
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room room2 = EntityBuilder.buildRoom(null, "202", 1, RoomClass.SUITE, 5);
        session.save(room1);
        session.flush();
        boolean isNew = roomService.isNewRoom(room2);
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewRoomFalse() throws ServiceException {
        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        Room room2 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(room1);
        session.flush();
        boolean isNew = roomService.isNewRoom(room2);
        Assert.assertFalse(isNew);
    }

    @Test
    public void testGetPagesOfRooms() throws ServiceException {
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
        List<Room> roomListActual = roomService.getPageOfRooms(2, 2);
        Assert.assertTrue(roomsListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomsListExpected));
    }

    @Test
    public void testGetNumberOfPagesWithRooms() throws ServiceException {
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
        int roomPagesNumber = roomService.getNumberOfPagesWithRooms(2);
        Assert.assertEquals(3, roomPagesNumber);
    }

    @Test
    public void testGetSuitedRooms() throws ServiceException {
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
        OrderRoomForm orderRoomForm = new OrderRoomForm(2, RoomClass.STANDART, checkIn, checkOut);
        session.flush();
        List<Room> roomListActual = roomService.getSuitedRooms(orderRoomForm);
        Assert.assertTrue(roomsListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomsListExpected));
    }

    @Test
    public void testComputeTotalPriceForRoom(){
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(room);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        int MILLIS_A_DAY = 24 * 60 * 60 * 1000;
        int daysNumber = ( int ) (room.getPrice()*((checkOut.getTime() - checkIn.getTime()) / MILLIS_A_DAY));
        Assert.assertEquals(roomService.computeTotalPriceForRoom(room, checkIn, checkOut), daysNumber);
    }

}
