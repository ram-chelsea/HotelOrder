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
@EqualsAndHashCode(callSuper = false)
public class CreditCard extends Entity {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer cardId;

    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "IS_VALID")
    private Boolean isValid;

    @Column(name = "AMOUNT")
    private Integer amount;
}

