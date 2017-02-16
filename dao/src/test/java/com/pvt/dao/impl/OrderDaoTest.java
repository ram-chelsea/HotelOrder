package com.pvt.dao.impl;

import com.pvt.constants.OrderStatus;
import com.pvt.constants.RoomClass;
import com.pvt.constants.SqlRequest;
import com.pvt.constants.UserRole;
import com.pvt.dao.EntityDaoImplTest;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;
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

public class OrderDaoTest extends EntityDaoImplTest {
    private HibernateUtil util = HibernateUtil.getHibernateUtil();

    @Before
    public void CleanTestDBB() {
        util.openSession();
        util.getSession().beginTransaction();
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
        OrderDao firstImpl = OrderDao.getInstance();
        OrderDao secondImpl = OrderDao.getInstance();
        Assert.assertEquals(firstImpl, secondImpl);
    }

    @Test
    public void testSave() {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(room);
        util.getSession().save(user);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        OrderDao.getInstance().save(expected);
        util.getSession().getTransaction().commit();
        Order actual = (Order) util.getSession().get(Order.class, expected.getOrderId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
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
        util.getSession().getTransaction().commit();
        List<Order> orderListActual = OrderDao.getInstance().getAll();
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetOrdersListByStatus() {
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
        util.getSession().getTransaction().commit();
        List<Order> orderListActual = OrderDao.getInstance().getOrdersListByStatus(OrderStatus.REQUESTED);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetClientOrdersListByStatus(){
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
        util.getSession().getTransaction().commit();
        List<Order> orderListActual = OrderDao.getInstance().getClientOrdersListByStatus(OrderStatus.REQUESTED, user1);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

//    @Test
//    public void testGetById(){
//        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
//        util.getSession().save(room);
//        util.getSession().save(user);
//        Date checkIn = Date.valueOf("2017-02-12");
//        Date checkOut = Date.valueOf("2017-02-18");
//        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
//        util.getSession().save(expected);
//        Order falseOrder = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CANCELLED, 60);
//        util.getSession().save(falseOrder);
//        util.getSession().getTransaction().commit();
//        Order actual = OrderDao.getInstance().get(expected.getOrderId());
//        Assert.assertEquals(expected, actual);
//    }

//    @Test
//    public void testDelete(){
//        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
//        util.getSession().save(room);
//        util.getSession().save(user);
//        Date checkIn = Date.valueOf("2017-02-12");
//        Date checkOut = Date.valueOf("2017-02-18");
//        Order order1 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
//        Order order2 = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
//        util.getSession().save(order1);
//        util.getSession().save(order2);
//        int id = order2.getOrderId();
//        util.getSession().getTransaction().commit();
//        OrderDao.getInstance().delete(id);
//        Order actual = (Order)util.getSession().get(Order.class, id);
//        Assert.assertNull(actual);
//    }

//    @Test
//    public void testUpdateOrderStatus(){
//        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
//        util.getSession().save(room);
//        util.getSession().save(user);
//        Date checkIn = Date.valueOf("2017-02-12");
//        Date checkOut = Date.valueOf("2017-02-18");
//        Order expected = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
//        util.getSession().save(expected);
//        OrderStatus expectedStatus = OrderStatus.CONFIRMED;
//        util.getSession().getTransaction().commit();
//        OrderDao.getInstance().updateOrderStatus(expected.getOrderId(), expectedStatus);
//        Order actual = (Order) util.getSession().get(Order.class, expected.getOrderId());
//        Assert.assertEquals(expectedStatus, actual.getOrderStatus());
//    }


}
