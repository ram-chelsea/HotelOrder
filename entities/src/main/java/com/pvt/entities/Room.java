package com.pvt.entities;


import com.pvt.constants.RoomClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Column(name = "ROOMINESS")
    private Integer roominess;

    @Column(name = "ROOM_CLASS")
    @Enumerated(EnumType.STRING)
    private RoomClass roomClass;

    @Column(name = "PRICE")
    private Integer price;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();
}
