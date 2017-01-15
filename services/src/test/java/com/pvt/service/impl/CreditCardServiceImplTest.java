package com.pvt.service.impl;

import com.pvt.constants.SqlRequest;
import com.pvt.dao.impl.CreditCardDaoImpl;
import com.pvt.entities.CreditCard;
import com.pvt.exceptions.DaoException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PoolManager;
import com.pvt.service.EntityServiceImplTest;
import com.pvt.services.impl.CreditCardServiceImpl;
import com.pvt.util.EntityBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreditCardServiceImplTest extends EntityServiceImplTest {

    @Before
    @After
    public void CleanTestDB() throws DaoException, SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_CARDS);
    }

    @Test
    public void testGetInstance() {
        CreditCardServiceImpl firstImpl = CreditCardServiceImpl.getInstance();
        CreditCardServiceImpl secondImpl = CreditCardServiceImpl.getInstance();
        Assert.assertEquals(firstImpl.hashCode(), secondImpl.hashCode());
    }

    @Test
    public void testAdd() throws DaoException, SQLException, ServiceException {
        CreditCard expected = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCardDaoImpl.getInstance().add(expected);
        CreditCard actual = CreditCardServiceImpl.getInstance().getByCardNumber(expected.getCardNumber());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testByCardNumber() throws DaoException, SQLException, ServiceException {
        CreditCard expected = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCard otherCard = EntityBuilder.buildCreditCard(1, "8765432112345678", true, 500);
        CreditCardDaoImpl.getInstance().add(expected);
        CreditCardDaoImpl.getInstance().add(otherCard);
        CreditCard actual = CreditCardServiceImpl.getInstance().getByCardNumber(expected.getCardNumber());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testTakeMoneyForOrder() throws DaoException, SQLException, ServiceException {
        CreditCard preCard = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCardDaoImpl.getInstance().add(preCard);
        CreditCard expected = CreditCardDaoImpl.getInstance().getByCardNumber(preCard.getCardNumber());
        int moneyToTake = 50;
        int oldAmount = expected.getAmount();
        CreditCardServiceImpl.getInstance().takeMoneyForOrder(expected, moneyToTake);
        CreditCard actual = CreditCardDaoImpl.getInstance().getById(expected.getCardId());
        Assert.assertEquals(oldAmount - moneyToTake, actual.getAmount());
    }

    @Test
    public void testIsNewCreditCardTrue() throws DaoException, SQLException, ServiceException {
        CreditCard card1 = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCard preCard = EntityBuilder.buildCreditCard(1, "8765432112345678", true, 500);
        CreditCardDaoImpl.getInstance().add(card1);
        CreditCardDaoImpl.getInstance().add(preCard);
        CreditCard card2 = CreditCardDaoImpl.getInstance().getByCardNumber(preCard.getCardNumber());
        CreditCardDaoImpl.getInstance().delete(card2.getCardId());
        boolean isNew = CreditCardServiceImpl.getInstance().isNewCreditCard(card2);
        Assert.assertTrue(isNew);
    }

    @Test
    public void testIsNewCreditCardFalse() throws DaoException, SQLException, ServiceException {
        CreditCard preCard = EntityBuilder.buildCreditCard(0, "1234567887654321", true, 500);
        CreditCardDaoImpl.getInstance().add(preCard);
        CreditCard expected = CreditCardDaoImpl.getInstance().getByCardNumber(preCard.getCardNumber());
        boolean isNew = CreditCardServiceImpl.getInstance().isNewCreditCard(expected);
        Assert.assertFalse(isNew);
    }

}

