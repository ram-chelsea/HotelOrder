package com.pvtoc.dto;

import com.pvtoc.constants.RoomClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
/**
 * Describes <tt>OrderRoomForm</tt> DTO object used during choosing room with requested parameters and for requested dates
 */
@Data
@NoArgsConstructor
public class OrderRoomForm {
    private Integer roominess;
    private RoomClass roomClass;
    private Date checkIn;
    private Date checkOut;

    public OrderRoomForm(final Integer roominess, final RoomClass roomClass, final Date checkIn, final Date checkOut) {
        this.roominess = roominess;
        this.roomClass = roomClass;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
