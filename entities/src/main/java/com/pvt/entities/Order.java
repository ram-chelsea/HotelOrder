package com.pvt.entities;

import com.pvt.constants.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
@ToString
public class Order extends com.pvt.entities.Entity {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue
    private Integer orderId;

    @ManyToOne
    @Cascade({CascadeType.PERSIST, CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @Cascade({CascadeType.PERSIST, CascadeType.SAVE_UPDATE})
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
