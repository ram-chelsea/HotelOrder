package com.pvtoc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Describes <tt>CreditCardAddingForm</tt> DTO object used during adding new <tt>CreditCard</tt> object
 */
@Data
@NoArgsConstructor
public class CreditCardAddingForm {

    private String cardNumber;
    private Integer amount;
    private Boolean isValid;

    public CreditCardAddingForm(final String cardNumber, final Integer amount, final Boolean isValid) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.isValid = isValid;
    }
}
