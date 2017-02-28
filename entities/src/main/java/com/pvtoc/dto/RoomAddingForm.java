package com.pvtoc.dto;


import com.pvtoc.constants.RoomClass;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes <tt>RoomAddingForm</tt> DTO object used during adding new <tt>Room</tt> object
 */
@Data
@NoArgsConstructor
public class RoomAddingForm {
    private String roomNumber;
    private Integer roominess;
    private RoomClass roomClass;
    private Integer roomPrice;

    public RoomAddingForm(final String roomNumber, final Integer roominess, final RoomClass roomClass, final Integer roomPrice) {
        this.roomNumber = roomNumber;
        this.roominess = roominess;
        this.roomClass = roomClass;
        this.roomPrice = roomPrice;
    }
}
