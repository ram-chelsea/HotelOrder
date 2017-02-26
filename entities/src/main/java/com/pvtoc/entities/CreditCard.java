package com.pvtoc.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Describes <tt>CreditCard</tt> entity
 */
@javax.persistence.Entity
@Table(name = "creditcards")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreditCard extends Entity {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CARD_ID")
    @GeneratedValue
    private Integer cardId;

    @Column(name = "CARD_NUMBER", unique = true)
    private String cardNumber;

    @Column(name = "IS_VALID")
    private Boolean isValid;

    @Column(name = "AMOUNT")
    private Integer amount;
}

