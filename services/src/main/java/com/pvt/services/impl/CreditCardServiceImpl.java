package com.pvt.services.impl;


import com.pvt.dao.impl.CreditCardDaoImpl;
import com.pvt.entities.CreditCard;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.GeneralService;
import com.pvt.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class CreditCardServiceImpl extends GeneralService<CreditCard> {
    private static Logger logger = Logger.getLogger(CreditCardServiceImpl.class);
    /**
     * Singleton object of <tt>CreditCardServiceImpl</tt> class
     */
    private static CreditCardServiceImpl instance;
    private static CreditCardDaoImpl cardDaoInst = CreditCardDaoImpl.getInstance();
    private Transaction transaction;
    public static HibernateUtil util = HibernateUtil.getHibernateUtil();

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
     * @throws ServiceException
     */
    @Override
    public void add(CreditCard card) throws ServiceException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            cardDaoInst.save(card);
            transaction.commit();
            logger.info("Save(card):" + card);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
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
     * @throws ServiceException
     */
    public void takeMoneyForOrder(CreditCard card, int amount) throws ServiceException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            cardDaoInst.takeMoneyForOrder(card, amount);
            transaction.commit();
            logger.info("takeMoneyForOrder(card, amount):" + card + ", " + amount);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Calls CreditCardDaoImpl getByCardNumber() method
     *
     * @param cardNumber -  <tt>cardNumber</tt>  being used to get <tt>CreditCard</tt> object
     * @return <tt>cardNumber</tt> with <i>cardNumber</i> value
     * @throws ServiceException
     */
    public CreditCard getByCardNumber(String cardNumber) throws ServiceException {
        CreditCard card;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            card = cardDaoInst.getByCardNumber(cardNumber);
            transaction.commit();
            logger.info("getByCardNumber(cardNumber): " + cardNumber);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return card;
    }

    /**
     * Calls CreditCardDaoImpl isNewCreditCard() method
     *
     * @param card -  <tt>CreditCard</tt>  to check if it is new
     * @return true if the <tt>CreditCard</tt> is new
     * @throws ServiceException
     */
    public boolean isNewCreditCard(CreditCard card) throws ServiceException {
        boolean isNew = false;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            if ((cardDaoInst.getById(card.getCardId()) == null) & (cardDaoInst.isNewCreditCard(card.getCardNumber()))) {
                isNew = true;
            }
            transaction.commit();
            logger.info("CheckIsNewCreditCard(card): " + card);
        } catch (HibernateException e) {
            transaction.rollback();
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isNew;
    }

}

