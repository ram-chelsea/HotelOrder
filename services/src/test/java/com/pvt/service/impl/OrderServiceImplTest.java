package com.pvt.service.impl;

import com.pvt.constants.OrderStatus;
import com.pvt.constants.RoomClass;
import com.pvt.constants.SqlRequest;
import com.pvt.constants.UserRole;
import com.pvt.dao.impl.OrderDaoImpl;
import com.pvt.dao.impl.RoomDaoImpl;
import com.pvt.dao.impl.UserDaoImpl;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.DaoException;
import com.pvt.exceptions.ServiceException;
import com.pvt.service.EntityServiceImplTest;
import com.pvt.services.impl.OrderServiceImpl;
import com.pvt.util.EntityBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImplTest extends EntityServiceImplTest {

    @Before
    @After
    public void CleanTestDB() throws DaoException, SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_ORDERS);
        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_USERS);
        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_ROOMS);

    }

    @Test
    public void testGetInstance() {
        OrderServiceImpl firstImpl = OrderServiceImpl.getInstance();
        OrderServiceImpl secondImpl = OrderServiceImpl.getInstance();
        Assert.assertEquals(firstImpl.hashCode(), secondImpl.hashCode());
    }

    @Test
    public void testAdd() throws DaoException, ServiceException, SQLException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        User preUser = EntityBuilder.buildUser(1, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser);
        RoomDaoImpl.getInstance().add(preRoom);
        User user = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
        Room room = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        Date checkIn = Date.valueOf("2017-01-12");
        Date checkOut = Date.valueOf("2017-01-18");
        Order expected = EntityBuilder.buildOrder(0, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        OrderServiceImpl.getInstance().add(expected);
        Order actual = OrderDaoImpl.getInstance().getAll().get(0);
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testGetAll() throws DaoException, SQLException, ServiceException {
        List<Order> orderListExpected = new ArrayList<>();
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room room = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        User preUser = EntityBuilder.buildUser(1, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser);
        User user = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
        Date checkIn = Date.valueOf("2017-01-12");
        Date checkOut = Date.valueOf("2017-01-18");
        Order order1 = EntityBuilder.buildOrder(0, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(1, user, room, checkIn, checkOut, OrderStatus.CANCELLED, 60);
        orderListExpected.add(order2);
        Order order3 = EntityBuilder.buildOrder(2, user, room, checkIn, checkOut, OrderStatus.DENIED, 90);
        orderListExpected.add(order3);
        for (Order order : orderListExpected) {
            OrderDaoImpl.getInstance().add(order);
        }
        List<Order> orderListActual = OrderServiceImpl.getInstance().getAll();

        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetOrdersListByStatus() throws DaoException, SQLException, ServiceException {
        List<Order> orderListExpected = new ArrayList<>();
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room room = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        User preUser = EntityBuilder.buildUser(1, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser);
        User user = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
        Date checkIn = Date.valueOf("2017-01-12");
        Date checkOut = Date.valueOf("2017-01-18");
        Order order1 = EntityBuilder.buildOrder(0, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(1, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 60);
        orderListExpected.add(order2);
        Order otherOrder1 = EntityBuilder.buildOrder(2, user, room, checkIn, checkOut, OrderStatus.DENIED, 90);
        OrderDaoImpl.getInstance().add(otherOrder1);
        Order otherOrder2 = EntityBuilder.buildOrder(3, user, room, checkIn, checkOut, OrderStatus.CONFIRMED, 120);
        OrderDaoImpl.getInstance().add(otherOrder2);
        for (Order order : orderListExpected) {
            OrderDaoImpl.getInstance().add(order);
        }
        List<Order> orderListActual = OrderServiceImpl.getInstance().getOrdersListByStatus(OrderStatus.REQUESTED);
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetClientOrdersListByStatus() throws DaoException, SQLException, ServiceException {
        List<Order> orderListExpected = new ArrayList<>();
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room room = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        User preUser1 = EntityBuilder.buildUser(1, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser1);
        User user1 = UserDaoImpl.getInstance().getByLogin(preUser1.getLogin());
        User preUser2 = EntityBuilder.buildUser(2, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser2);
        User user2 = UserDaoImpl.getInstance().getByLogin(preUser2.getLogin());
        Date checkIn = Date.valueOf("2017-01-12");
        Date checkOut = Date.valueOf("2017-01-18");
        Order order1 = EntityBuilder.buildOrder(0, user1, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        orderListExpected.add(order1);
        Order order2 = EntityBuilder.buildOrder(1, user1, room, checkIn, checkOut, OrderStatus.REQUESTED, 40);
        orderListExpected.add(order2);
        Order otherOrder1 = EntityBuilder.buildOrder(2, user1, room, checkIn, checkOut, OrderStatus.DENIED, 50);
        OrderDaoImpl.getInstance().add(otherOrder1);
        Order otherOrder2 = EntityBuilder.buildOrder(3, user2, room, checkIn, checkOut, OrderStatus.REQUESTED, 60);
        OrderDaoImpl.getInstance().add(otherOrder2);
        Order otherOrder3 = EntityBuilder.buildOrder(4, user2, room, checkIn, checkOut, OrderStatus.COMPLETED, 50);
        OrderDaoImpl.getInstance().add(otherOrder3);
        for (Order order : orderListExpected) {
            OrderDaoImpl.getInstance().add(order);
        }
        List<Order> orderListActual = OrderServiceImpl.getInstance().getClientOrdersListByStatus(OrderStatus.REQUESTED, user1.getUserId());
        Assert.assertTrue(orderListExpected.containsAll(orderListActual) && orderListActual.containsAll(orderListExpected));
    }

    @Test
    public void testGetById() throws DaoException, SQLException, ServiceException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room room = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        User preUser = EntityBuilder.buildUser(1, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser);
        User user = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
        Date checkIn = Date.valueOf("2017-01-12");
        Date checkOut = Date.valueOf("2017-01-18");
        Order preOrder = EntityBuilder.buildOrder(0, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        OrderDaoImpl.getInstance().add(preOrder);
        Order otherOrder = EntityBuilder.buildOrder(1, user, room, checkIn, checkOut, OrderStatus.CANCELLED, 60);
        OrderDaoImpl.getInstance().add(otherOrder);
        Order expected = OrderDaoImpl.getInstance().getAll().get(0);
        Order actual = OrderServiceImpl.getInstance().getById(expected.getOrderId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateOrderStatus() throws DaoException, SQLException, ServiceException {
        Room preRoom = EntityBuilder.buildRoom(1, "201", 1, RoomClass.SUITE, 5);
        RoomDaoImpl.getInstance().add(preRoom);
        Room room = RoomDaoImpl.getInstance().getByRoomNumber(preRoom.getRoomNumber());
        User preUser = EntityBuilder.buildUser(1, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser);
        User user = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
        Date checkIn = Date.valueOf("2017-01-12");
        Date checkOut = Date.valueOf("2017-01-18");
        Order preOrder = EntityBuilder.buildOrder(0, user, room, checkIn, checkOut, OrderStatus.REQUESTED, 30);
        OrderDaoImpl.getInstance().add(preOrder);
        Order expected = OrderDaoImpl.getInstance().getAll().get(0);
        OrderStatus expectedStatus = OrderStatus.CONFIRMED;
        OrderServiceImpl.getInstance().updateOrderStatus(expected.getOrderId(), expectedStatus);
        Order actual = OrderDaoImpl.getInstance().getById(expected.getOrderId());
        Assert.assertEquals(expectedStatus, actual.getOrderStatus());
    }


}
