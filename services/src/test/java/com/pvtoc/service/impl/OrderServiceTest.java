package com.pvtoc.service.impl;

import com.pvtoc.constants.OrderStatus;
import com.pvtoc.constants.RoomClass;
import com.pvtoc.constants.UserRole;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.Room;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.OrderService;
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
public class OrderServiceTest {
    @Autowired
    OrderService<Order> orderService;
    @Autowired
    SessionFactory sessionFactory;

    private Session session;

    @Before
    public void BeforeTest() {
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void testAdd() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        session.flush();
        orderService.add(expected);
        Order actual = ( Order ) session.get(Order.class, expected.getOrderId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws ServiceException {
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
        List<Order> orderListActual = orderService.getAll();
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetOrdersListByStatus() throws ServiceException {
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
        List<Order> orderListActual = orderService.getOrdersListByStatus(OrderStatus.REQUESTED);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetClientOrdersListByStatus() throws ServiceException {
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
        List<Order> orderListActual = orderService.getClientOrdersListByStatus(OrderStatus.REQUESTED, user1);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGet() throws ServiceException {
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
        Order actual = orderService.get(Order.class, expected.getOrderId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateOrderStatus() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        session.save(expected);
        OrderStatus expectedStatus = OrderStatus.CONFIRMED;
        session.flush();
        orderService.updateOrderStatus(expected.getOrderId(), expectedStatus);
        Order actual = ( Order ) session.get(Order.class, expected.getOrderId());
        Assert.assertEquals(expectedStatus, actual.getOrderStatus());
    }

    @Test
    public void createOrderIfRoomIsFreeTrue() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn1 = Date.valueOf("2017-02-01");
        Date checkOut1 = Date.valueOf("2017-02-02");
        Date checkIn2 = Date.valueOf("2017-02-03");
        Date checkOut2 = Date.valueOf("2017-02-04");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn1, checkOut1, OrderStatus.REQUESTED, 5);
        session.save(order1);
        session.flush();
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn2, checkOut2, OrderStatus.REQUESTED, 5);
        boolean isFree = orderService.createOrderIfRoomIsFree(order2);
        Order actual = ( Order ) session.get(Order.class, order2.getOrderId());
        Assert.assertTrue(actual.equals(order2) && isFree);
    }

    @Test
    public void createOrderIfRoomIsFreeFalse() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(room);
        session.save(user);
        Date checkIn1 = Date.valueOf("2017-02-01");
        Date checkOut1 = Date.valueOf("2017-02-03");
        Date checkIn2 = Date.valueOf("2017-02-02");
        Date checkOut2 = Date.valueOf("2017-02-04");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn1, checkOut1, OrderStatus.REQUESTED, 10);
        session.save(order1);
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn2, checkOut2, OrderStatus.REQUESTED, 10);
        session.flush();
        boolean isFree = orderService.createOrderIfRoomIsFree(order2);
        Boolean isTransient = session.contains(order2);
        Assert.assertFalse(isTransient || isFree);
    }

    @Test
    public void testIsRoomFreeForPeriodInOrderTrue_1() throws ServiceException {
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
        Assert.assertTrue(orderService.checkIsRoomFreeForPeriodInOrder(orderNew));
    }

    @Test
    public void testIsRoomFreeForPeriodInOrderTrue_2() throws ServiceException {
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
        Assert.assertTrue(orderService.checkIsRoomFreeForPeriodInOrder(orderNew));
    }

    @Test
    public void testIsRoomFreeForPeriodInOrderFalse() throws ServiceException {
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
        Assert.assertFalse(orderService.checkIsRoomFreeForPeriodInOrder(orderNew));
    }

}
