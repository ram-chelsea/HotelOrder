package com.pvt.dao;

import com.pvt.entities.CreditCard;

public interface CreditCardDao<T extends CreditCard> extends Dao<T> {

    T getByCardNumber(String cardNumber);

    boolean isNewCreditCard(String cardNumber);

}
