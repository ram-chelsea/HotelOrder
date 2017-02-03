package com.pvt.service.impl;


import com.pvt.constants.OrderStatus;
import com.pvt.constants.RoomClass;
import com.pvt.constants.SqlRequest;
import com.pvt.constants.UserRole;
import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.service.ServiceImplTest;
import com.pvt.services.impl.CreditCardServiceImpl;
import com.pvt.services.impl.PayOrderServiceImpl;
import com.pvt.util.EntityBuilder;
import com.pvt.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

public class PayOrderServiceTest extends ServiceImplTest {
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
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_ORDERS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_USERS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_ROOMS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_CARDS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_TRUE);
        query.executeUpdate();
        session.close();
    }

    @Test
    public void testGetInstance() {
        CreditCardServiceImpl firstImpl = CreditCardServiceImpl.getInstance();
        CreditCardServiceImpl secondImpl = CreditCardServiceImpl.getInstance();
        Assert.assertEquals(firstImpl, secondImpl);
    }

    @Test
    public void payOrderWithCreditCardTestTrue() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        int cardAmount = 200;
        CreditCard card = EntityBuilder.buildCreditCard(null, "1000100010001000", true, cardAmount);
        util.getSession().save(room);
        util.getSession().save(user);
        util.getSession().save(card);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CONFIRMED, 30);
        util.getSession().save(order);
        util.getSession().flush();
        boolean isEnoughMoney = PayOrderServiceImpl.getInstance().payOrderWithCreditCard(card, order);
        boolean statusChanged = (order.getOrderStatus() == OrderStatus.PAID);
        boolean isMoneyTaken = (card.getAmount() == cardAmount - order.getTotalPrice());
        Assert.assertTrue(isEnoughMoney && statusChanged && isMoneyTaken);
    }
    @Test
    public void payOrderWithCreditCardTestFalse() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        int cardAmount = 25;
        CreditCard card = EntityBuilder.buildCreditCard(null, "1000100010001000", true, cardAmount);
        util.getSession().save(room);
        util.getSession().save(user);
        util.getSession().save(card);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CONFIRMED, 30);
        util.getSession().save(order);
        util.getSession().flush();
        boolean isEnoughMoney = PayOrderServiceImpl.getInstance().payOrderWithCreditCard(card, order);
        boolean statusNotChanged = (order.getOrderStatus() == OrderStatus.CONFIRMED);
        boolean isMoneyNotTaken = (card.getAmount() == cardAmount);
        Assert.assertTrue(!isEnoughMoney && statusNotChanged && isMoneyNotTaken);
    }



}

