package com.pvtoc.service.impl;


import com.pvtoc.constants.OrderStatus;
import com.pvtoc.constants.RoomClass;
import com.pvtoc.constants.UserRole;
import com.pvtoc.entities.CreditCard;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.Room;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.PayOrderService;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/service-test-app-context.xml")
@Transactional
@Rollback
public class PayOrderServiceTest{
    @Autowired
    PayOrderService<CreditCard,Order> payOrderService;
    @Autowired
    SessionFactory sessionFactory;

    private Session session;

    @Before
    public void BeforeTest() {
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void payOrderWithCreditCardTestTrue() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        int cardAmount = 200;
        CreditCard card = EntityBuilder.buildCreditCard(null, "1000100010001000", true, cardAmount);
        session.save(room);
        session.save(user);
        session.save(card);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CONFIRMED, 30);
        session.save(order);
        session.flush();
        boolean isEnoughMoney = payOrderService.payOrderWithCreditCard(card, order);
        boolean statusChanged = (order.getOrderStatus() == OrderStatus.PAID);
        boolean isMoneyTaken = (card.getAmount() == cardAmount - order.getTotalPrice());
        Assert.assertTrue(isEnoughMoney && statusChanged && isMoneyTaken);
    }
    @Test
    public void payOrderWithCreditCardTestFalse() throws ServiceException {
        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
        User user = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        int cardAmount = 25;
        CreditCard card = EntityBuilder.buildCreditCard(null, "1000100010001000", true, cardAmount);
        session.save(room);
        session.save(user);
        session.save(card);
        Date checkIn = Date.valueOf("2017-02-12");
        Date checkOut = Date.valueOf("2017-02-18");
        Order order = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.CONFIRMED, 30);
        session.save(order);
        session.flush();
        boolean isEnoughMoney = payOrderService.payOrderWithCreditCard(card, order);
        boolean statusNotChanged = (order.getOrderStatus() == OrderStatus.CONFIRMED);
        boolean isMoneyNotTaken = (card.getAmount() == cardAmount);
        Assert.assertTrue(!isEnoughMoney && statusNotChanged && isMoneyNotTaken);
    }



}

