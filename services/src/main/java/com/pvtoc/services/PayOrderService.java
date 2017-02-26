package com.pvtoc.services;


import com.pvtoc.exceptions.ServiceException;

public interface PayOrderService<T, K> {
    /**
     * Calls PayOrderService payOrderWithCreditCard method
     * @param card <tt>CreditCard</tt> object, which used to pay <i>order</i>
     * @param order <tt>Order</tt> which is paid
     * @return true if payment operation was committed successfully
     * @throws ServiceException
     */
    boolean payOrderWithCreditCard(T card, K order) throws ServiceException;
}
