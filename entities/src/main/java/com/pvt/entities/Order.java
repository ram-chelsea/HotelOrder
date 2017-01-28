package com.pvt.entities;


import com.pvt.constants.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * Describes <tt>Order</tt> entity
 */
@javax.persistence.Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends Entity {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Column(name = "CHECK_IN_DATE")
    private Date checkInDate;

    @Column(name = "CHECK_OUT_DATE")
    private Date checkOutDate;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;

}
