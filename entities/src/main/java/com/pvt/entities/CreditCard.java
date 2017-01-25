package com.pvt.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Describes <tt>CreditCard</tt> entity
 */
@javax.persistence.Entity
@Table(name = "creditcards")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "cardId", callSuper = false)
public class CreditCard extends Entity {
    @Id
    @GeneratedValue
    private int cardId;

    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "IS_VALID")
    private boolean isValid;

    @Column(name = "AMOUNT")
    private int amount;
}
