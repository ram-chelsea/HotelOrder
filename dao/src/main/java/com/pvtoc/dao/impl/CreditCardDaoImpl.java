package com.pvtoc.dao.impl;

import com.pvtoc.constants.HqlRequest;
import com.pvtoc.dao.CreditCardDao;
import com.pvtoc.dao.GeneralDao;
import com.pvtoc.entities.CreditCard;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Describes <tt>GeneralDao</tt>-child repository class being used for executing
 * of CRUD operations with <tt>CreditCard</tt> object
 */
@Repository
@NoArgsConstructor
public class CreditCardDaoImpl extends GeneralDao<CreditCard> implements CreditCardDao<CreditCard>{

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
        Query query = getSession().createQuery(HqlRequest.GET_CREDIT_CARD_BY_NUMBER);
        query.setParameter(0, cardNumber);
        CreditCard card = (CreditCard) query.uniqueResult();
        return card;
    }

    /**
     * Checks if the <tt>CreditCard</tt> with the corresponding <i>cardNumber</i> doesn't exist
     * in the database
     *
     * @param cardNumber being used for checking if the <tt>CreditCard</tt> object
     *                   with the corresponding <tt>cardNumber</tt> property value doesn't exist in the database
     * @return true if <tt>CreditCard</tt> with <i>cardNumber</i> doesn't exist in the database
     */
    public boolean isNewCreditCard(String cardNumber) {
        Query query = getSession().createQuery(HqlRequest.CHECK_IS_NEW_CREDIT_CARD);
        query.setParameter(0, cardNumber);
        long count = (Long) query.uniqueResult();
        boolean isNewCard = (count == 0);
        return isNewCard;
    }

}
