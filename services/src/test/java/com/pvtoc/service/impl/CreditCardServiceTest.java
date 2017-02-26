package com.pvtoc.service.impl;

import com.pvtoc.entities.CreditCard;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.CreditCardService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/service-test-app-context.xml")
@Transactional
@Rollback
public class CreditCardServiceTest {
    @Autowired
    CreditCardService<CreditCard> cardService;
    @Autowired
    SessionFactory sessionFactory;

    private Session session;

    @Before
    public void BeforeTest() {
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void testAdd() throws ServiceException {
        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        cardService.add(expected);
        CreditCard actual = ( CreditCard ) session.get(CreditCard.class, expected.getCardId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testByCardNumber() throws ServiceException {
        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard otherCard = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
        session.save(expected);
        session.save(otherCard);
        session.flush();
        CreditCard actual = cardService.getByCardNumber(expected.getCardNumber());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsNewCreditCardTrue() throws ServiceException {
        CreditCard card1 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard card2 = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
        session.flush();
        cardService.add(card1);
        boolean isNew = cardService.isNewCreditCard(card2);
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewCreditCardFalse() throws ServiceException {
        CreditCard card1 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard card2 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        session.flush();
        cardService.add(card1);
        boolean isNew = cardService.isNewCreditCard(card2);
        Assert.assertFalse(isNew);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsGetAllIsUnsupported() throws  ServiceException{
        cardService.getAll();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsGetIsUnsupported() throws  ServiceException{
        cardService.get(CreditCard.class, 1);
    }

}

