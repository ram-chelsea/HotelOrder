package com.pvtoc.services;

import com.pvtoc.entities.CreditCard;
import com.pvtoc.exceptions.ServiceException;

public interface CreditCardService<T extends CreditCard> extends EntityService<T> {
    /**
     * Calls CreditCardDaoImpl getByCardNumber() method
     *
     * @param cardNumber -  <tt>cardNumber</tt>  being used to get <tt>CreditCard</tt> object
     * @return <tt>cardNumber</tt> with <i>cardNumber</i> value
     * @throws ServiceException
     */
    T getByCardNumber(String cardNumber) throws ServiceException;
    /**
     * Calls CreditCardDaoImpl isNewCreditCard() method
     *
     * @param card -  <tt>CreditCard</tt>  to check if it is new
     * @return true if the <tt>CreditCard</tt> is new
     * @throws ServiceException
     */
    boolean isNewCreditCard(T card) throws ServiceException;
}
