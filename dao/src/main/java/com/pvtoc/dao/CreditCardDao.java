package com.pvtoc.dao;

import com.pvtoc.entities.CreditCard;
/**
 * Describes the interface of DAO layer being used in the projects
 * to implement CRUD operations with <tt>CreditCard</tt> entities
 *
 * @param <T> - <tt>CreditCard</tt>-child class object, being processed in the CRUD operations
 */
public interface CreditCardDao<T extends CreditCard> extends Dao<T> {
    /**
     * Returns the Object of <tt>CreditCard</tt> class from the database by its <i>cardNumber</i> value
     *
     * @param cardNumber value of <tt>CreditCard</tt> property being used to get the object from the database
     * @return <tt>CreditCard</tt> object, having corresponding <i>cardNumber</i> value
     */
    T getByCardNumber(String cardNumber);
    /**
     * Checks if the <tt>CreditCard</tt> with the corresponding <i>cardNumber</i> doesn't exist
     * in the database
     *
     * @param cardNumber being used for checking if the <tt>CreditCard</tt> object
     *                   with the corresponding <tt>cardNumber</tt> property value doesn't exist in the database
     * @return true if <tt>CreditCard</tt> with <i>cardNumber</i> doesn't exist in the database
     */
    boolean isNewCreditCard(String cardNumber);

}
