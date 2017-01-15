package com.pvt.dao.impl;

import com.pvt.constants.SqlRequest;
import com.pvt.dao.EntityDaoImplTest;
import com.pvt.entities.CreditCard;
import com.pvt.exceptions.DaoException;
import com.pvt.managers.PoolManager;
import com.pvt.util.EntityBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreditCardDaoImplTest extends EntityDaoImplTest {

    @Before
    @After
    public void CleanTestDB() throws DaoException, SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_CARDS);
    }

    @Test
    public void testGetInstance() {
        CreditCardDaoImpl firstImpl = CreditCardDaoImpl.getInstance();
        CreditCardDaoImpl secondImpl = CreditCardDaoImpl.getInstance();
        Assert.assertEquals(firstImpl.hashCode(), secondImpl.hashCode());
    }

    @Test
    public void testAdd() throws DaoException {
        CreditCard expected = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCardDaoImpl.getInstance().add(expected);
        CreditCard actual = CreditCardDaoImpl.getInstance().getByCardNumber(expected.getCardNumber());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testGetById() throws DaoException {
        CreditCard preCard = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCard otherCard = EntityBuilder.buildCreditCard(1, "8765432112345678", true, 500);
        CreditCardDaoImpl.getInstance().add(preCard);
        CreditCardDaoImpl.getInstance().add(otherCard);
        CreditCard expected = CreditCardDaoImpl.getInstance().getByCardNumber(preCard.getCardNumber());
        CreditCard actual = CreditCardDaoImpl.getInstance().getById(expected.getCardId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testByCardNumber() throws DaoException {
        CreditCard expected = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCard otherCard = EntityBuilder.buildCreditCard(1, "8765432112345678", true, 500);
        CreditCardDaoImpl.getInstance().add(expected);
        CreditCardDaoImpl.getInstance().add(otherCard);
        CreditCard actual = CreditCardDaoImpl.getInstance().getByCardNumber(expected.getCardNumber());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testTakeMoneyForOrder() throws DaoException, SQLException {
        CreditCard preCard = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCardDaoImpl.getInstance().add(preCard);
        CreditCard expected = CreditCardDaoImpl.getInstance().getByCardNumber(preCard.getCardNumber());
        int moneyToTake = 50;
        int oldAmount = expected.getAmount();
        CreditCardDaoImpl.getInstance().takeMoneyForOrder(expected, moneyToTake);
        CreditCard actual = CreditCardDaoImpl.getInstance().getById(expected.getCardId());
        Assert.assertEquals(oldAmount - moneyToTake, actual.getAmount());
    }

    @Test
    public void testIsNewCreditCardTrue() throws DaoException {
        CreditCard card1 = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCard card2 = EntityBuilder.buildCreditCard(1, "8765432112345678", true, 500);
        CreditCardDaoImpl.getInstance().add(card1);
        boolean isNew = CreditCardDaoImpl.getInstance().isNewCreditCard(card2.getCardNumber());
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewCreditCardFalse() throws DaoException {
        CreditCard expected = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCardDaoImpl.getInstance().add(expected);
        boolean isNew = CreditCardDaoImpl.getInstance().isNewCreditCard(expected.getCardNumber());
        Assert.assertFalse(isNew);
    }

}
