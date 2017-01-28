package com.pvt.entities;


import com.pvt.constants.RoomClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Describes <tt>Room</tt> entity
 */
@javax.persistence.Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "roomId", callSuper = false)
public class Room extends Entity {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer roomId;

    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Column(name = "ROOMINESS")
    private Integer roominess;

    @Column(name = "ROOM_CLASS")
    @Enumerated(EnumType.STRING)
    private RoomClass roomClass;

    @Column(name = "PRICE")
    private Integer price;
}
