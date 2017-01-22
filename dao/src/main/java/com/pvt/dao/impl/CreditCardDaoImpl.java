package com.pvt.dao.impl;

import com.pvt.constants.ColumnName;
import com.pvt.constants.SqlRequest;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.CreditCard;
import com.pvt.exceptions.DaoException;
import com.pvt.managers.PoolManager;
import com.pvt.util.EntityBuilder;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>CreditCard</tt> object
 */
public class CreditCardDaoImpl extends GeneralDao<CreditCard> {
    private static Logger logger = Logger.getLogger(CreditCardDaoImpl.class);
        /**
     * String property being used for describing the error in case of SQL Exception for <i>Log4j</i>
     */
    private static String message;
    /**
     * Singleton object of <tt>CreditCardDaoImpl</tt> class
     */
    private static CreditCardDaoImpl instance;

    /**
     * Creates a CreditCardDaoImpl variable
     */
    private CreditCardDaoImpl() {
    }

    /**
     * Describes synchronized method of getting <tt>CreditCardDaoImpl</tt> singleton object
     *
     * @return <tt>CreditCardDaoImpl</tt> singleton object
     */
    public static synchronized CreditCardDaoImpl getInstance() {
        if (instance == null) {
            instance = new CreditCardDaoImpl();
        }
        return instance;
    }

    /**
     * <tt>Dao</tt> interface method not being used for this class
     *
     * @return <tt>List</tt> of all  <tt>CreditCard</tt> objects being contained in the database
     * @throws DaoException
     */
    @Override
    public List<CreditCard> getAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the <tt>CreditCard</tt> object properties values into database
     *
     * @param card <tt>CreditCard</tt> element, which properties will be pushed into the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public void add(CreditCard card) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.ADD_CREDIT_CARD);
            statement.setString(1, card.getCardNumber());
            statement.setBoolean(2, card.isValid());
            statement.setInt(3, card.getAmount());
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to add the room ";
            logger.error(message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Delete the Object of <tt>CreditCard</tt> class from the database by <i>cardId</i> value
     *
     * @param cardId value of <tt>CreditCard</tt> property being used to delete the corresponding object from the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public void delete(int cardId) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.DELETE_CARD_BY_ID);
            statement.setInt(1, cardId);
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to delete the card ";
            logger.error(message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Returns the Object of <tt>CreditCard</tt> class from the database by its <i>cardId</i> value
     *
     * @param cardId value of <tt>CreditCard</tt> property being used to get the object from the database
     * @return <tt>CreditCard</tt> object, having corresponding <i>cardId</i> value
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    @Override
    public CreditCard getById(int cardId) throws DaoException {
        CreditCard card = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_CREDIT_CARD_BY_ID);
            statement.setInt(1, cardId);
            result = statement.executeQuery();
            while (result.next()) {
                card = buildCreditCard(result);
            }
        } catch (SQLException e) {
            message = "Unable to return the card ";
            logger.error(message);
            throw new DaoException(message, e);
        }
        return card;

    }

    /**
     * Returns the Object of <tt>CreditCard</tt> class from the database by its <i>cardNumber</i> value
     *
     * @param cardNumber value of <tt>CreditCard</tt> property being used to get the object from the database
     * @return <tt>CreditCard</tt> object, having corresponding <i>cardNumber</i> value
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public CreditCard getByCardNumber(String cardNumber) throws DaoException {
        CreditCard card = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_CREDIT_CARD_BY_NUMBER);
            statement.setString(1, cardNumber);
            result = statement.executeQuery();
            while (result.next()) {
                card = buildCreditCard(result);
            }
        } catch (SQLException e) {
            message = "Unable to return the card ";
            logger.error(message);
            throw new DaoException(message, e);
        }
        return card;
    }

    /**
     * Decrease the <tt>CreditCard</tt> <i>amount</i> property value on <tt>amount</tt> value
     *
     * @param card   <tt>CreditCard</tt> object being used to take <tt>amount</tt> of money from
     * @param amount amount of money being taken from <tt>card</tt>
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public void takeMoneyForOrder(CreditCard card, int amount) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.TAKE_MONEY_FROM_CREDIT_CARD);
            statement.setInt(1, card.getAmount() - amount);
            statement.setInt(2, card.getCardId());
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to return the take money from the card ";
            logger.error(message);
            throw new DaoException(message, e);
        }
    }

    /**
     * Checks if the <tt>CreditCard</tt> with the corresponding <i>cardNumber</i> hasn't existed
     * in the database
     *
     * @param cardNumber being used for checking if the <tt>CreditCard</tt> object
     *                   with the corresponding <tt>cardNumber</tt> property value doesn't exist in the database
     * @return true if <tt>CreditCard</tt> with <i>cardNumber</i> doesn't exist in the database
     * @throws DaoException if a database access error occurs
     *                      or this method is called on a closed connection
     */
    public boolean isNewCreditCard(String cardNumber) throws DaoException {
        boolean isNew = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_CREDIT_CARD_BY_NUMBER);
            statement.setString(1, cardNumber);
            result = statement.executeQuery();
            if (!result.next()) {
                isNew = true;
            }
        } catch (SQLException e) {
            message = "Unable to check the card ";
            logger.error(message);
            throw new DaoException(message, e);
        }
        return isNew;
    }

    /**
     * Build <tt>CreditCard</tt> object with the properties values corresponding
     * to ResultSet being got after SQL Request executing
     *
     * @param result <tt>ResultSet</tt> being got after SQL Request executing
     * @return <tt>CreditCard</tt> object with the properties values corresponding
     * to ResultSet being got after SQL Request executing
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    private CreditCard buildCreditCard(ResultSet result) throws SQLException {
        int cardId = result.getInt(ColumnName.CREDIT_CARD_ID);
        String cardNumber = result.getString(ColumnName.CREDIT_CARD_NUMBER);
        boolean isValid = result.getBoolean(ColumnName.CREDIT_CARD_VALIDITY);
        int amount = result.getInt(ColumnName.CREDIT_CARD_MONEY_AMOUNT);
        return EntityBuilder.buildCreditCard(cardId, cardNumber, isValid, amount);
    }

}
