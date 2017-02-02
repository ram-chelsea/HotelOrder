package com.pvt.services;


import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.exceptions.ServiceException;

public interface PayOrderService {
    /**
     * Calls PayOrderService payOrderWithCreditCard method
     * @param card <tt>CreditCard</tt> object, which used to pay <i>order</i>
     * @param order <tt>Order</tt> which is paid
     * @return true if payment operation was committed successfully
     * @throws ServiceException
     */
    boolean payOrderWithCreditCard(CreditCard card, Order order) throws ServiceException;
}
