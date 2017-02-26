package com.pvtoc.dao.impl;

import com.pvtoc.constants.OrderStatus;
import com.pvtoc.constants.RoomClass;
import com.pvtoc.constants.UserRole;
import com.pvtoc.dao.OrderDao;
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
public class OrderDaoTest {
    @Autowired
    OrderDao<Order> orderDao;

    private Session session;

    @Before
    public void BeforeTest() {
        session = orderDao.getSession();
    }

    @Test
    public void testSave() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderDao.save(expected);
        session.flush();
        Order actual = ( Order ) session.get(Order.class, expected.getOrderId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
        List<Order> orderListExpected = new ArrayList<>();
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        session.save(room);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CANCELLED, 60);
        orderListExpected.add(order2);
        Order order3 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.DENIED, 90);
        orderListExpected.add(order3);
        for (Order order : orderListExpected) {
            session.save(order);
        }
        session.flush();
        List<Order> orderListActual = orderDao.getAll();
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetOrdersListByStatus() {
        List<Order> orderListExpected = new ArrayList<>();
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 60);
        orderListExpected.add(order2);
        Order otherOrder1 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.DENIED, 90);
        session.save(otherOrder1);
        Order otherOrder2 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CONFIRMED, 120);
        session.save(otherOrder2);
        for (Order order : orderListExpected) {
            session.save(order);
        }
        session.flush();
        List<Order> orderListActual = orderDao.getOrdersListByStatus(OrderStatus.REQUESTED);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetClientOrdersListByStatus() {
        List<Order> orderListExpected = new ArrayList<>();
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user1);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user2);
        Date checkIn = Date.valueOf("2017-01-12");
        Date checkOut = Date.valueOf("2017-01-18");
        Order order1 = EntityBuilder.buildOrder(null, user1, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(null, user1, room, checkIn, checkOut, OrderStatus.REQUESTED, 40);
        orderListExpected.add(order2);
        Order otherOrder1 = EntityBuilder.buildOrder(null, user1, room, checkIn, checkOut, OrderStatus.DENIED, 50);
        session.save(otherOrder1);
        Order otherOrder2 = EntityBuilder.buildOrder(null, user2, room, checkIn, checkOut, OrderStatus.REQUESTED, 60);
        session.save(otherOrder2);
        Order otherOrder3 = EntityBuilder.buildOrder(null, user2, room, checkIn, checkOut, OrderStatus.EXPIRED, 50);
        session.save(otherOrder3);
        for (Order order : orderListExpected) {
            session.save(order);
        }
        session.flush();
        List<Order> orderListActual = orderDao.getClientOrdersListByStatus(OrderStatus.REQUESTED, user1);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGet() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        session.save(expected);
        Order falseOrder = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CANCELLED, 60);
        session.save(falseOrder);
        session.flush();
        Order actual = orderDao.get(Order.class, expected.getOrderId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testDelete() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        session.save(order1);
        session.save(order2);
        int id = order2.getOrderId();
        session.flush();
        orderDao.delete(order2);
        Order actual = ( Order ) session.get(Order.class, id);
        Assert.assertNull(actual);
    }

    @Test
    public void testUpdate() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        session.save(expected);
        OrderStatus expectedStatus = OrderStatus.CONFIRMED;
        expected.setOrderStatus(expectedStatus);
        orderDao.update(expected);
        session.flush();
        Order actual = ( Order ) session.get(Order.class, expected.getOrderId());
        Assert.assertEquals(expectedStatus, actual.getOrderStatus());
    }

    @Test
    public void testIsRoomFreeForPeriodInOrderTrue_1() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        session.save(order);
        Date checkInNew = Date.valueOf("2017-02-19");
        Date checkOutNew = Date.valueOf("2017-02-21");
        Order orderNew = EntityBuilder.buildOrder(null, user, room, checkInNew, checkOutNew, null, null);
        session.flush();
        Assert.assertTrue(orderDao.isRoomFreeForPeriodInOrder(orderNew));
    }

    @Test
    public void testIsRoomFreeForPeriodInOrderTrue_2() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.EXPIRED, 30);
        session.save(order);
        Date checkInNew = Date.valueOf("2017-02-13");
        Date checkOutNew = Date.valueOf("2017-02-21");
        Order orderNew = EntityBuilder.buildOrder(null, user, room, checkInNew, checkOutNew, null, null);
        session.flush();
        Assert.assertTrue(orderDao.isRoomFreeForPeriodInOrder(orderNew));
    }

    @Test
    public void testIsRoomFreeForPeriodInOrderFalse() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        session.save(order);
        Date checkInNew = Date.valueOf("2017-02-11");
        Date checkOutNew = Date.valueOf("2017-02-19");
        Order orderNew = EntityBuilder.buildOrder(null, user, room, checkInNew, checkOutNew, null, null);
        session.flush();
        Assert.assertFalse(orderDao.isRoomFreeForPeriodInOrder(orderNew));
    }
}
