package com.pvt.constants;

/**
 * Contains constants representing HQL requests being used in the project
 */
public final class HqlRequest {
    public static final String GET_ALL_CLIENTS =
            "SELECT U FROM User U WHERE U.userRole = 'CLIENT' ";
    public static final String GET_USER_BY_LOGIN =
            "SELECT U FROM User U WHERE U.login = ?";
    public static final String CHECK_LOGIN =
            "SELECT count(U) FROM User U WHERE U.login = ?";
    public static final String CHECK_AUTHENTICATION =
            "SELECT count(U) FROM User U WHERE U.login = ? AND U.password = ?";

    public static final String GET_ALL_ROOMS =
            "FROM Room";
    public static final String GET_ROOM_BY_NUMBER =
            "SELECT R FROM Room R WHERE R.roomNumber = ?";
    public static final String CHECK_IS_NEW_ROOM =
            "SELECT count(R) FROM Room R WHERE R.roomNumber = ?";
    public static final String GET_ALL_FREE_ROOMS_FOR_PERIOD_WITH_CATEGORIES
            = "SELECT R FROM Room R WHERE R.roominess = ? AND R.roomClass = ? " +
                    "AND R NOT IN (SELECT DISTINCT O.room FROM Order O " +
                        "WHERE O.orderStatus IN ('REQUESTED', 'CONFIRMED', 'ORDERED') AND (O.checkOutDate >= ? AND O.checkInDate <= ?))";
    public static final String GET_ALL_ROOMINESSES =
            "SELECT DISTINCT R.roominess FROM Room R ORDER BY R.roominess";
    public static final String CHECK_IS_FREE_ROOM_FOR_PERIOD_IN_ORDER =
            "SELECT count(O) FROM Order O WHERE O.room = ? AND O.orderStatus IN ('REQUESTED', 'CONFIRMED', 'ORDERED')" +
                    " AND (O.checkOutDate >= ? AND O.checkInDate <= ?)";

    public static final String GET_ALL_ORDERS =
            "FROM Order";
    public static final String GET_ORDERS_BY_STATUS =
            "SELECT O FROM Order O WHERE O.orderStatus = ?";
    public static final String GET_CLIENTS_ORDERS_BY_STATUS =
            "SELECT O FROM Order O WHERE O.orderStatus = ? AND O.user = ?";

    public static final String GET_CREDIT_CARD_BY_NUMBER =
            "SELECT C FROM CreditCard C WHERE C.cardNumber = ?";
    public static final String CHECK_IS_NEW_CREDIT_CARD =
            "SELECT count(C) FROM CreditCard C WHERE C.cardNumber = ?";

}
