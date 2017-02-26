package com.pvtoc.dao.impl;

import com.pvtoc.dao.CreditCardDao;
import com.pvtoc.entities.CreditCard;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dao-test-app-context.xml")
@Transactional
@Rollback
public class CreditCardDaoTest {
    @Autowired
    CreditCardDao<CreditCard> cardDao;

    private Session session;

    @Before
    public void BeforeTest() {
        session = cardDao.getSession();
    }

    @Test
    public void testSave() {
        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        cardDao.save(expected);
        session.flush();
        System.out.println(session.hashCode());
        CreditCard actual = ( CreditCard ) session.get(CreditCard.class, expected.getCardId());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testGet() {
        CreditCard falseCard = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard expected = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
        session.save(falseCard);
        session.save(expected);
        session.flush();
        CreditCard actual = cardDao.get(CreditCard.class, expected.getCardId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testByCardNumber() {
        CreditCard falseCard = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard expected = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
        session.save(falseCard);
        session.save(expected);
        session.flush();
        CreditCard actual = cardDao.getByCardNumber(expected.getCardNumber());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        session.save(expected);
        int moneyToTake = 50;
        int oldAmount = expected.getAmount();
        expected.setAmount(oldAmount - moneyToTake);
        cardDao.update(expected);
        session.flush();
        Assert.assertEquals(oldAmount - moneyToTake, ( int ) expected.getAmount());
    }

    @Test
    public void testIsNewCreditCardTrue() {
        CreditCard card1 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard card2 = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
        session.save(card1);
        session.flush();
        boolean isNew = cardDao.isNewCreditCard(card2.getCardNumber());
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewCreditCardFalse() {
        CreditCard card1 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
        CreditCard card2 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 1000);
        session.save(card1);
        session.flush();
        boolean isNew = cardDao.isNewCreditCard(card2.getCardNumber());
        Assert.assertFalse(isNew);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsGetAllIsUnsupported(){
        cardDao.getAll();
    }

}
