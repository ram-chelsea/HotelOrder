package com.pvt.service.impl;

import com.pvt.constants.SqlRequest;
import com.pvt.entities.CreditCard;
import com.pvt.exceptions.ServiceException;
import com.pvt.service.EntityServiceImplTest;
import com.pvt.services.impl.CreditCardServiceImpl;
import com.pvt.util.EntityBuilder;
import com.pvt.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreditCardServiceImplTest extends EntityServiceImplTest {
    private HibernateUtil util = HibernateUtil.getHibernateUtil();

    @Before
    public void BeforeTest() {
        util.openSession();
    }

    @After
    public void AfterTestCleanDB() {
        Session session = util.getSession();
        Query query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_CARDS);
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
    public void testAdd() throws ServiceException {
        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCardServiceImpl.getInstance().add(expected);
        CreditCard actual = (CreditCard) util.getSession().get(CreditCard.class, expected.getCardId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testByCardNumber() throws ServiceException {
        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard otherCard = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
        util.getSession().save(expected);
        util.getSession().save(otherCard);
        util.getSession().flush();
        CreditCard actual = CreditCardServiceImpl.getInstance().getByCardNumber(expected.getCardNumber());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTakeMoneyForOrder() throws ServiceException {
        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        util.getSession().save(expected);
        int moneyToTake = 50;
        int oldAmount = expected.getAmount();
        util.getSession().flush();
        CreditCardServiceImpl.getInstance().takeMoneyForOrder(expected, moneyToTake);
        Assert.assertEquals(oldAmount - moneyToTake, (int) expected.getAmount());
    }

    @Test
    public void testIsNewCreditCardTrue() throws ServiceException {
        CreditCard card1 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard card2 = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
        util.getSession().flush();
        CreditCardServiceImpl.getInstance().add(card1);
        boolean isNew = CreditCardServiceImpl.getInstance().isNewCreditCard(card2);
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewCreditCardFalse() throws ServiceException {
        CreditCard card1 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard card2 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        util.getSession().flush();
        CreditCardServiceImpl.getInstance().add(card1);
        boolean isNew = CreditCardServiceImpl.getInstance().isNewCreditCard(card2);
        Assert.assertFalse(isNew);
    }

}

