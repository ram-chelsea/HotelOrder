package com.pvt.services;

import com.pvt.entities.CreditCard;
import com.pvt.exceptions.ServiceException;

public interface CreditCardService<T extends CreditCard> extends EntityService<T> {

    T getByCardNumber(String cardNumber) throws ServiceException;

    boolean isNewCreditCard(T card) throws ServiceException;
}
