package com.pvt.services.impl;


import com.pvt.dao.CreditCardDao;
import com.pvt.entities.CreditCard;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.AbstractEntityService;
import com.pvt.services.CreditCardService;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Transactional
public class CreditCardServiceImpl extends AbstractEntityService<CreditCard> implements CreditCardService<CreditCard> {
    private static Logger logger = Logger.getLogger(CreditCardServiceImpl.class);

    @Autowired
    CreditCardDao creditCardDao;

    @Override
    public List<CreditCard> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CreditCard get(Class<CreditCard> clazz, int id) {
        throw new UnsupportedOperationException();
    }


    /**
     * Calls CreditCardDaoImpl getByCardNumber() method
     *
     * @param cardNumber -  <tt>cardNumber</tt>  being used to get <tt>CreditCard</tt> object
     * @return <tt>cardNumber</tt> with <i>cardNumber</i> value
     * @throws ServiceException
     */
    @Override
    public CreditCard getByCardNumber(String cardNumber) throws ServiceException {
        CreditCard card;
        try {
            card = creditCardDao.getByCardNumber(cardNumber);
            logger.info("getByCardNumber(cardNumber): " + cardNumber);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return card;
    }

    /**
     * Calls CreditCardDaoImpl isNewCreditCard() method
     *
     * @param card -  <tt>CreditCard</tt>  to check if it is new
     * @return true if the <tt>CreditCard</tt> is new
     * @throws ServiceException
     */
    @Override
    public boolean isNewCreditCard(CreditCard card) throws ServiceException {
        boolean isNew = false;
        try {
            if (creditCardDao.isNewCreditCard(card.getCardNumber())) {
                isNew = true;
            }
            logger.info("CheckIsNewCreditCard(card): " + card);
        } catch (HibernateException e) {
            logger.error(transactionFailedMessage + e);
            throw new ServiceException(e.getMessage());
        }
        return isNew;
    }

}

