package com.pvtoc.services.impl;


import com.pvtoc.constants.OrderStatus;
import com.pvtoc.dao.CreditCardDao;
import com.pvtoc.dao.OrderDao;
import com.pvtoc.entities.CreditCard;
import com.pvtoc.entities.Order;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.PayOrderService;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
public class PayOrderServiceImpl implements PayOrderService<CreditCard, Order> {
    private static Logger logger = Logger.getLogger(PayOrderServiceImpl.class);
    private static final String transactionFailedMessage = "Transaction failed: ";
    @Autowired
    OrderDao orderDao;
    @Autowired
    CreditCardDao creditCardDao;

    /**
     * Calls PayOrderService payOrderWithCreditCard method
     * @param card <tt>CreditCard</tt> object, which used to pay <i>order</i>
     * @param order <tt>Order</tt> which is paid
     * @return true if payment operation was committed successfully
     * @throws ServiceException
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean payOrderWithCreditCard(CreditCard card, Order order) throws ServiceException {
        boolean isEnoughMoney;
        try {
            creditCardDao.getSession().refresh(card, LockOptions.UPGRADE);
            isEnoughMoney = isEnoughMoneyToPayOrder(card, order);
            if (isEnoughMoney) {
                order.setOrderStatus(OrderStatus.PAID);
                takeMoneyForOrder(card,order.getTotalPrice());
                orderDao.update(order);
                creditCardDao.update(card);
            }
            logger.info("IsEnoughMoneyToPayOrder: " + isEnoughMoney);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isEnoughMoney;
    }

    /**
     * Checks if enough money on the card to pay the order
     * @param card <tt>CreditCard</tt> object, which used to pay <i>order</i>
     * @param order <tt>Order</tt> which is paid
     * @return true, if there is enough money to pay the order
     */
    private boolean isEnoughMoneyToPayOrder(CreditCard card, Order order) {
        boolean isEnoughMoneyToPayOrder = false;
        if (card.getAmount() >= order.getTotalPrice()) {
            isEnoughMoneyToPayOrder = true;
        }
        return isEnoughMoneyToPayOrder;
    }

    /**
     * Decreses <tt>CreditCard</tt> <tt>amount</tt> property for <i>amount</i> value
     * @param card <tt>CreditCard</tt> object used to decrease its <tt>amount</tt>
     * @param amount value to decrease <tt>CreditCard</tt> <tt>amount</tt> property
     */
    private void takeMoneyForOrder(CreditCard card, int amount){
        int newAmount = card.getAmount() - amount;
        card.setAmount(newAmount);
    }
}
