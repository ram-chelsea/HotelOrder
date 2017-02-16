package com.pvt.service.impl;

import com.pvt.constants.OrderStatus;
import com.pvt.constants.RoomClass;
import com.pvt.constants.SqlRequest;
import com.pvt.constants.UserRole;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.service.ServiceImplTest;
import com.pvt.services.impl.OrderServiceImpl;
import com.pvt.util.EntityBuilder;
import com.pvt.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImplTest extends ServiceImplTest {
    private HibernateUtil util = HibernateUtil.getHibernateUtil();

    @Before
    public void CleanTestDBB() {
        util.openSession();
    }

    @After
    public void CleanTestDBA() {
        Session session = util.getSession();
        Query query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_FALSE);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_ORDERS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_USERS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_ROOMS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_TRUE);
        query.executeUpdate();
        session.close();
    }

    @Test
    public void testGetInstance() {
        OrderServiceImpl firstImpl = OrderServiceImpl.getInstance();
        OrderServiceImpl secondImpl = OrderServiceImpl.getInstance();
        Assert.assertEquals(firstImpl, secondImpl);
    }

    @Test
    public void testAdd() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(room);
        util.getSession().save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        util.getSession().flush();
        OrderServiceImpl.getInstance().add(expected);
        Order actual = (Order) util.getSession().get(Order.class, expected.getOrderId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws ServiceException {
        List<Order> orderListExpected = new ArrayList<>();
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        util.getSession().save(room);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CANCELLED, 60);
        orderListExpected.add(order2);
        Order order3 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.DENIED, 90);
        orderListExpected.add(order3);
        for (Order order : orderListExpected) {
            util.getSession().save(order);
        }
        util.getSession().flush();
        List<Order> orderListActual = OrderServiceImpl.getInstance().getAll();
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetOrdersListByStatus() throws ServiceException {
        List<Order> orderListExpected = new ArrayList<>();
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(room);
        util.getSession().save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 60);
        orderListExpected.add(order2);
        Order otherOrder1 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.DENIED, 90);
        util.getSession().save(otherOrder1);
        Order otherOrder2 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CONFIRMED, 120);
        util.getSession().save(otherOrder2);
        for (Order order : orderListExpected) {
            util.getSession().save(order);
        }
        util.getSession().flush();
        List<Order> orderListActual = OrderServiceImpl.getInstance().getOrdersListByStatus(OrderStatus.REQUESTED);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetClientOrdersListByStatus() throws ServiceException {
        List<Order> orderListExpected = new ArrayList<>();
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(room);
        util.getSession().save(user1);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user2);
        Date checkIn = Date.valueOf("2017-01-12");
        Date checkOut = Date.valueOf("2017-01-18");
        Order order1 = EntityBuilder.buildOrder(null, user1, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(null, user1, room, checkIn, checkOut, OrderStatus.REQUESTED, 40);
        orderListExpected.add(order2);
        Order otherOrder1 = EntityBuilder.buildOrder(null, user1, room, checkIn, checkOut, OrderStatus.DENIED, 50);
        util.getSession().save(otherOrder1);
        Order otherOrder2 = EntityBuilder.buildOrder(null, user2, room, checkIn, checkOut, OrderStatus.REQUESTED, 60);
        util.getSession().save(otherOrder2);
        Order otherOrder3 = EntityBuilder.buildOrder(null, user2, room, checkIn, checkOut, OrderStatus.EXPIRED, 50);
        util.getSession().save(otherOrder3);
        for (Order order : orderListExpected) {
            util.getSession().save(order);
        }
        util.getSession().flush();
        List<Order> orderListActual = OrderServiceImpl.getInstance().getClientOrdersListByStatus(OrderStatus.REQUESTED, user1);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetById() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(room);
        util.getSession().save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        util.getSession().save(expected);
        Order falseOrder = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CANCELLED, 60);
        util.getSession().save(falseOrder);
        util.getSession().flush();
        Order actual = OrderServiceImpl.getInstance().getById(expected.getOrderId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateOrderStatus() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(room);
        util.getSession().save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        util.getSession().save(expected);
        OrderStatus expectedStatus = OrderStatus.CONFIRMED;
        util.getSession().flush();
        OrderServiceImpl.getInstance().updateOrderStatus(expected.getOrderId(), expectedStatus);
        Order actual = (Order) util.getSession().get(Order.class, expected.getOrderId());
        Assert.assertEquals(expectedStatus, actual.getOrderStatus());
    }

    @Test
    public void createOrderIfRoomIsFreeTrue() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(room);
        util.getSession().save(user);
        Date checkIn1 = Date.valueOf("2017-02-01");
        Date checkOut1 = Date.valueOf("2017-02-02");
        Date checkIn2 = Date.valueOf("2017-02-03");
        Date checkOut2 = Date.valueOf("2017-02-04");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn1, checkOut1, OrderStatus.REQUESTED, 5);
        util.getSession().save(order1);
        util.getSession().flush();
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn2, checkOut2, OrderStatus.REQUESTED, 5);
        boolean isFree = OrderServiceImpl.getInstance().createOrderIfRoomIsFree(order2);
        Order actual = (Order) util.getSession().get(Order.class, order2.getOrderId());
        Assert.assertTrue(actual.equals(order2)&& isFree);
    }

    @Test
    public void createOrderIfRoomIsFreeFalse() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(room);
        util.getSession().save(user);
        Date checkIn1 = Date.valueOf("2017-02-01");
        Date checkOut1 = Date.valueOf("2017-02-03");
        Date checkIn2 = Date.valueOf("2017-02-02");
        Date checkOut2 = Date.valueOf("2017-02-04");
        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn1, checkOut1, OrderStatus.REQUESTED, 10);
        util.getSession().save(order1);
        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn2, checkOut2, OrderStatus.REQUESTED, 10);
        util.getSession().flush();
        boolean isFree = OrderServiceImpl.getInstance().createOrderIfRoomIsFree(order2);
        Boolean isTransient = util.getSession().contains(order2);
        Assert.assertFalse(isTransient|| isFree);
    }



}
