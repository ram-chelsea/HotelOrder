package com.pvt.services.impl;


import com.pvt.dao.impl.CreditCardDaoImpl;
import com.pvt.entities.CreditCard;
import com.pvt.exceptions.DaoException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PoolManager;
import com.pvt.services.GeneralService;
import com.pvt.utils.ProjectLogger;

import java.sql.SQLException;
import java.util.List;

public class CreditCardServiceImpl extends GeneralService<CreditCard> {
    /**
     * Singleton object of <tt>CreditCardServiceImpl</tt> class
     */
    private static CreditCardServiceImpl instance;

    /**
     * Creates a CreditCardServiceImpl variable
     */
    private CreditCardServiceImpl() {
    }

    /**
     * Describes synchronized method of getting <tt>CreditCardServiceImpl</tt> singleton object
     *
     * @return <tt>CreditCardServiceImpl</tt> singleton object
     */
    public static synchronized CreditCardServiceImpl getInstance() {
        if (instance == null) {
            instance = new CreditCardServiceImpl();
        }
        return instance;
    }

    /**
     * Calls CreditCardDaoImpl add() method
     *
     * @param card - <tt>CreditCard</tt> object to add
     * @throws SQLException
     * @throws ServiceException
     */
    @Override
    public void add(CreditCard card) throws SQLException, ServiceException {
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            CreditCardDaoImpl.getInstance().add(card);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public List<CreditCard> getAll() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CreditCard getById(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls CreditCardDaoImpl takeMoneyForOrder() method
     *
     * @param card   - <tt>CreditCard</tt> object to take money from
     * @param amount money to take from <i>card</i>
     * @throws SQLException
     * @throws ServiceException
     */
    public void takeMoneyForOrder(CreditCard card, int amount) throws ServiceException, SQLException {
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            CreditCardDaoImpl.getInstance().takeMoneyForOrder(card, amount);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
    }

    /**
     * Calls CreditCardDaoImpl getByCardNumber() method
     *
     * @param cardNumber -  <tt>cardNumber</tt>  being used to get <tt>CreditCard</tt> object
     * @return <tt>cardNumber</tt> with <i>cardNumber</i> value
     * @throws SQLException
     * @throws ServiceException
     */
    public CreditCard getByCardNumber(String cardNumber) throws SQLException, ServiceException {
        CreditCard card;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            card = CreditCardDaoImpl.getInstance().getByCardNumber(cardNumber);
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return card;
    }

    /**
     * Calls CreditCardDaoImpl isNewCreditCard() method
     *
     * @param card -  <tt>CreditCard</tt>  to check if it is new
     * @return true if the <tt>CreditCard</tt> is new
     * @throws SQLException
     * @throws ServiceException
     */
    public boolean isNewCreditCard(CreditCard card) throws SQLException, ServiceException {
        boolean isNew = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            if ((CreditCardDaoImpl.getInstance().getById(card.getCardId()) == null) & (CreditCardDaoImpl.getInstance().isNewCreditCard(card.getCardNumber()))) {
                isNew = true;
            }
            connection.commit();
        } catch (SQLException | DaoException e) {
            connection.rollback();
            ProjectLogger.getInstance().logError(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            PoolManager.getInstance().releaseConnection(connection);
        }
        return isNew;
    }

}

