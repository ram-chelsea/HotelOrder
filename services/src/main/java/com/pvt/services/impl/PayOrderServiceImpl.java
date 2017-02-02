package com.pvt.services.impl;


import com.pvt.constants.OrderStatus;
import com.pvt.dao.impl.CreditCardDaoImpl;
import com.pvt.dao.impl.OrderDaoImpl;
import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.PayOrderService;
import com.pvt.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;

public class PayOrderServiceImpl implements PayOrderService {
    private static Logger logger = Logger.getLogger(PayOrderServiceImpl.class);
    private static final String transactionFailedMessage = "Transaction failed: ";
    private static HibernateUtil util = HibernateUtil.getHibernateUtil();
    private static PayOrderServiceImpl instance;

    private PayOrderServiceImpl() {

    }
    public static synchronized PayOrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new PayOrderServiceImpl();
        }
        return instance;
    }
    /**
     * Calls PayOrderService payOrderWithCreditCard method
     * @param card <tt>CreditCard</tt> object, which used to pay <i>order</i>
     * @param order <tt>Order</tt> which is paid
     * @return true if payment operation was committed successfully
     * @throws ServiceException
     */
    @Override
    public boolean payOrderWithCreditCard(CreditCard card, Order order) throws ServiceException {
        boolean isEnoughMoney;
        try {
            util.getSession().beginTransaction();
            util.getSession().refresh(card, LockOptions.UPGRADE);
            isEnoughMoney = isEnoughMoneyToPayOrder(card, order);
            if (isEnoughMoney) {
                OrderDaoImpl.getInstance().updateOrderStatus(order.getOrderId(), OrderStatus.PAID);
                CreditCardDaoImpl.getInstance().takeMoneyForOrder(card, order.getTotalPrice());
            }
            util.getSession().getTransaction().commit();
            logger.info("IsEnoughMoneyToPayOrder: " + isEnoughMoney);
        } catch (HibernateException e) {
            util.getSession().getTransaction().rollback();
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
}
