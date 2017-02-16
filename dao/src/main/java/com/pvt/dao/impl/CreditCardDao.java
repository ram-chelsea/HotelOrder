package com.pvt.dao.impl;

import com.pvt.constants.HqlRequest;
import com.pvt.dao.GeneralDao;
import com.pvt.entities.CreditCard;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child singleton class being used for executing
 * of CRUD operations with <tt>CreditCard</tt> object
 */

public class CreditCardDao extends GeneralDao<CreditCard> {

    /**
     * Singleton object of <tt>CreditCardDao</tt> class
     */
    private static CreditCardDao instance;

    /**
     * Creates a CreditCardDao variable
     */
    private CreditCardDao() {
    }

    /**
     * Describes synchronized method of getting <tt>CreditCardDao</tt> singleton object
     *
     * @return <tt>CreditCardDao</tt> singleton object
     */
    public static synchronized CreditCardDao getInstance() {
        if (instance == null) {
            instance = new CreditCardDao();
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
     * Returns the Object of <tt>CreditCard</tt> class from the database by its <i>cardNumber</i> value
     *
     * @param cardNumber value of <tt>CreditCard</tt> property being used to get the object from the database
     * @return <tt>CreditCard</tt> object, having corresponding <i>cardNumber</i> value
     */
    public CreditCard getByCardNumber(String cardNumber) {
        Session session = util.getSession();
        Query query = session.createQuery(HqlRequest.GET_CREDIT_CARD_BY_NUMBER)
                .setCacheable(true);
        query.setParameter(0, cardNumber);
        CreditCard card = (CreditCard) query.uniqueResult();
        return card;
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
        long count = (Long) query.uniqueResult();
        boolean isNewCard = (count == 0);
        return isNewCard;
    }

}
