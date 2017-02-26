package com.pvtoc.constants;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Describes the enumeration of values <tt<Room/tt> <tt>roomClass</tt> property value can accept
 */
public enum RoomClass {
    SUITE, STANDART, DELUXE;

    /**
     * Return <tt>ArrayList</tt> of RoomClass values
     *
     * @return <tt>ArrayList</tt> of RoomClass values
     */
    public static ArrayList enumToList() {
        return new ArrayList<>(Arrays.asList(RoomClass.values()));
    }
}
