package com.pvt.constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Describes the enumeration of values <tt<Order/tt> <tt>status</tt> property value can accept
 */
public enum OrderStatus {
    REQUESTED, CONFIRMED, DENIED, CANCELLED, PAID, COMPLETED;

    /**
     * Return <tt>ArrayList</tt> of OrderStatus values
     *
     * @return <tt>ArrayList</tt> of OrderStatus values
     */
    public static ArrayList enumToList() {
        return new ArrayList<>(Arrays.asList(OrderStatus.values()));
    }
}
