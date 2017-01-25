package com.pvt.entities;


import com.pvt.constants.RoomClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @GeneratedValue
    private int roomId;

    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Column(name = "ROOMINESS")
    private int roominess;

    @Column(name = "ROOM_CLASS")
    private RoomClass roomClass;

    @Column(name = "PRICE")
    private int price;
}
