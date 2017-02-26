package com.pvtoc.entities;


import com.pvtoc.constants.RoomClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Describes <tt>Room</tt> entity
 */
@javax.persistence.Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "orders", callSuper = false)
@ToString(exclude = "orders")
public class Room extends Entity {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ROOM_ID")
    @GeneratedValue
    private Integer roomId;

    @Column(name = "ROOM_NUMBER", unique = true)
    private String roomNumber;

    @Column(name = "ROOMINESS")
    private Integer roominess;

    @Column(name = "ROOM_CLASS")
    @Enumerated(EnumType.STRING)
    private RoomClass roomClass;

    @Column(name = "PRICE")
    private Integer price;

}
