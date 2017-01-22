package com.pvt.entities;

/**
 * Describes <tt>CreditCard</tt> entity
 */
public class CreditCard extends Entity {

    private int cardId;
    private String cardNumber;
    private boolean isValid;
    private int amount;

    @Override
    public int hashCode() {
        int result = 1;
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((amount == 0) ? 0 : Integer.valueOf(amount).hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((!isValid) ? 0 : Boolean.valueOf(isValid).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CreditCard)) {
            return false;
        }
        CreditCard other = (CreditCard) obj;

        if (cardNumber == null) {
            if (other.cardNumber != null) {
                return false;
            }
        } else if (!cardNumber.equals(other.cardNumber)) {
            return false;
        }
        if (amount == 0) {
            if (other.amount != 0) {
                return false;
            }
        } else if (amount != other.amount) {
            return false;
        }
        if (isValid != other.isValid) {
            return false;
        }
        return true;
    }

    /**
     * @return the <tt>CreditCard</tt> id
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * @param cardId id to set
     */

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    /**
     * @return the <tt>CreditCard</tt> card number
     */

    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber card number to set
     */

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return true if the <tt>CreditCard</tt> is valid
     */

    public boolean isValid() {
        return isValid;
    }

    /**
     * @param valid validity to set
     */

    public void setValid(boolean valid) {
        isValid = valid;
    }

    /**
     * @return the <tt>CreditCard</tt> amount
     */

    public int getAmount() {
        return amount;
    }

    /**
     * @param amount amount to set
     */

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
