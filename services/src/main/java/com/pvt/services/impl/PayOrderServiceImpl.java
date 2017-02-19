package com.pvt.services.impl;


import com.pvt.constants.OrderStatus;
import com.pvt.dao.CreditCardDao;
import com.pvt.dao.OrderDao;
import com.pvt.dao.impl.CreditCardDaoImpl;
import com.pvt.dao.impl.OrderDaoImpl;
import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.PayOrderService;
import com.pvt.util.HibernateUtil;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
public class PayOrderServiceImpl implements PayOrderService {
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
    @Transactional
    public boolean payOrderWithCreditCard(CreditCard card, Order order) throws ServiceException {
        boolean isEnoughMoney;
        try {
            util.getSession().refresh(card, LockOptions.UPGRADE);//TODO input lockoptions into @transactional
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

    private void takeMoneyForOrder(CreditCard card, int amount){
        int newAmount = card.getAmount() - amount;
        card.setAmount(newAmount);
    }
}
