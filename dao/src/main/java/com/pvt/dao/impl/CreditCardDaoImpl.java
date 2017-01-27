package com.pvt.dao.impl;

import com.pvt.constants.HqlRequest;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.CreditCard;
import com.pvt.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>CreditCard</tt> object
 */
public class CreditCardDaoImpl extends GeneralDao<CreditCard> {

    /**
     * Singleton object of <tt>CreditCardDaoImpl</tt> class
     */
    private static CreditCardDaoImpl instance;
    private static HibernateUtil util = HibernateUtil.getHibernateUtil();

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
     */
    public List<CreditCard> getAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the <tt>CreditCard</tt> object properties values into database
     *
     * @param card <tt>CreditCard</tt> element, which properties will be pushed into the database
     */
    public void save(CreditCard card) {
        Session session = util.getSession();
        session.save(card);
    }

    /**
     * Delete the Object of <tt>CreditCard</tt> class from the database by <i>cardId</i> value
     *
     * @param cardId value of <tt>CreditCard</tt> property being used to delete the corresponding object from the database
     */
    public void delete(int cardId) {
        Session session = util.getSession();
        CreditCard card = (CreditCard) session.get(CreditCard.class, cardId);
        session.delete(card);
    }

    /**
     * Returns the Object of <tt>CreditCard</tt> class from the database by its <i>cardId</i> value
     *
     * @param cardId value of <tt>CreditCard</tt> property being used to get the object from the database
     * @return <tt>CreditCard</tt> object, having corresponding <i>cardId</i> value
     */
    public CreditCard getById(int cardId) {
        Session session = util.getSession();
        CreditCard card = (CreditCard) session.get(CreditCard.class, cardId);
        return card;
    }

    /**
     * Returns the Object of <tt>CreditCard</tt> class from the database by its <i>cardNumber</i> value
     *
     * @param cardNumber value of <tt>CreditCard</tt> property being used to get the object from the database
     * @return <tt>CreditCard</tt> object, having corresponding <i>cardNumber</i> value
     */
    public CreditCard getByCardNumber(String cardNumber) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_CREDIT_CARD_BY_NUMBER);
        query.setParameter(0, cardNumber);
        CreditCard card = (CreditCard) query.uniqueResult();
        return card;
    }

    /**
     * Decrease the <tt>CreditCard</tt> <i>amount</i> property value on <tt>amount</tt> value
     *
     * @param card   <tt>CreditCard</tt> object being used to take <tt>amount</tt> of money from
     * @param amount amount of money being taken from <tt>card</tt>
     */
    public void takeMoneyForOrder(CreditCard card, int amount) {
        Session session = util.getSession();
        int newAmount = card.getAmount() - amount;
        card.setAmount(newAmount);
        session.update(card);
    }

    /**
     * Checks if the <tt>CreditCard</tt> with the corresponding <i>cardNumber</i> hasn't existed
     * in the database
     *
     * @param cardNumber being used for checking if the <tt>CreditCard</tt> object
     *                   with the corresponding <tt>cardNumber</tt> property value doesn't exist in the database
     * @return true if <tt>CreditCard</tt> with <i>cardNumber</i> doesn't exist in the database
     */
    public boolean isNewCreditCard(String cardNumber) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.CHECK_IS_NEW_CREDIT_CARD);
        query.setParameter(0, cardNumber);
        int count = ((Long) query.uniqueResult()).intValue();
        boolean isNewCard = (count == 0);
        return isNewCard;
    }

}
