package com.pvt.constants;

/**
 * Contains constants representing SQL requests being used in the project
 */
public class SqlRequest {
    public static final String GET_ALL_CLIENTS = "SELECT * FROM users WHERE USER_ROLE = \"CLIENT\" ORDER BY LAST_NAME";
    public static final String GET_USER_BY_ID = "SELECT * FROM users WHERE ID = ?";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE LOGIN = ?";
    public static final String ADD_USER = "INSERT INTO users(LOGIN, FIRST_NAME, LAST_NAME, PASSWORD, USER_ROLE) VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE ID = ?";
    public static final String CHECK_LOGIN = "SELECT LOGIN FROM users WHERE LOGIN = ?";
    public static final String CHECK_AUTHENTICATION = "SELECT LOGIN, PASSWORD FROM users WHERE LOGIN = ? AND PASSWORD = ?";

    public static final String GET_ALL_ORDERS = "SELECT * FROM orders";
    public static final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE ID = ?";
    public static final String GET_ORDERS_BY_STATUS = "SELECT * FROM orders WHERE STATUS = ?";
    public static final String GET_CLIENTS_ORDERS_BY_STATUS = "SELECT * FROM orders WHERE STATUS = ? AND USER_ID = ?";
    public static final String GET_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE USER_ID = ?";
    public static final String GET_ORDERS_BY_ROOM_ID = "SELECT * FROM orders WHERE ROOM_ID = ?";
    public static final String ADD_ORDER = "INSERT INTO orders(USER_ID, ROOM_ID, CHECK_IN_DATE, CHECK_OUT_DATE, STATUS, TOTAL_PRICE) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ORDER_STATUS = "UPDATE orders SET STATUS = ? WHERE ID = ?";
    public static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE ID = ?";

    public static final String GET_ALL_ROOMS = "SELECT * FROM rooms";
    public static final String GET_ALL_NOT_BOOKED_ROOMS = "SELECT * FROM rooms WHERE ID  NOT IN (SELECT DISTINCT ROOM_ID FROM orders WHERE STATUS IN (\"REQUESTED\", \"CONFIRMED\", \"ORDERED\") )";
    public static final String GET_ALL_FREE_ROOMS_FOR_PERIOD = "SELECT * FROM rooms WHERE ID  NOT IN (SELECT DISTINCT ROOM_ID FROM orders WHERE STATUS IN (\"REQUESTED\", \"CONFIRMED\", \"ORDERED\") AND (CHECK_OUT_DATE >= ? AND CHECK_IN_DATE <= ?))";
    public static final String GET_ROOM_BY_ID = "SELECT * FROM rooms WHERE ID = ?";
    public static final String GET_ROOM_BY_NUMBER = "SELECT * FROM rooms WHERE ROOM_NUMBER = ?";
    public static final String GET_ALL_FREE_ROOMS_FOR_PERIOD_WITH_CATEGORIES = "SELECT * FROM rooms WHERE ROOMINESS = ? AND ROOM_CLASS = ? AND ID  NOT IN (SELECT DISTINCT ROOM_ID FROM orders WHERE STATUS IN (\"REQUESTED\", \"CONFIRMED\", \"ORDERED\") AND (CHECK_OUT_DATE >= ? AND CHECK_IN_DATE <= ?))";
    public static final String GET_ALL_FREE_ROOMS_FOR_PERIOD_WITH_CATEGORIES_IN_PRICE_INTERVAL = "SELECT * FROM rooms WHERE ROOMINESS = ? AND ROOM_CLASS = ? AND PRICE IN (?,?) AND ID  NOT IN (SELECT DISTINCT ROOM_ID FROM orders WHERE ORDER_STATUS IN (\"REQUESTED\", \"CONFIRMED\", \"ORDERED\") AND (CHECK_OUT_DATE >= ? AND CHECK_IN_DATE <= ?))";
    public static final String ADD_ROOM = "INSERT INTO rooms (ROOM_NUMBER, ROOMINESS, ROOM_CLASS, PRICE) VALUES (?, ?, ?, ?)";
    public static final String DELETE_ROOM_BY_ID = "DELETE FROM rooms WHERE ID = ?";
    public static final String UPDATE_ROOM_PRICE = "UPDATE rooms SET PRICE = ? WHERE ID = ?";
    public static final String GET_ALL_ROOMINESSES = "SELECT DISTINCT ROOMINESS FROM rooms ORDER BY ROOMINESS";

    public static final String ADD_CREDIT_CARD = "INSERT INTO creditcards (CARD_NUMBER, IS_VALID, AMOUNT) VALUES (?, ?, ?)";
    public static final String GET_CREDIT_CARD_BY_NUMBER = "SELECT * FROM creditcards WHERE CARD_NUMBER = ?";
    public static final String GET_CREDIT_CARD_BY_ID = "SELECT * FROM creditcards WHERE ID = ?";
    public static final String TAKE_MONEY_FROM_CREDIT_CARD = "UPDATE creditcards SET AMOUNT = ? WHERE ID = ?";
    public static final String DELETE_CARD_BY_ID = "DELETE FROM creditcards WHERE ID = ?";

    public static final String TRUNCATE_TEST_USERS = "TRUNCATE TABLE users";
    public static final String TRUNCATE_TEST_ORDERS = "TRUNCATE TABLE orders";
    public static final String TRUNCATE_TEST_ROOMS = "TRUNCATE TABLE rooms";
    public static final String TRUNCATE_TEST_CARDS = "TRUNCATE TABLE creditcards";

    private SqlRequest() {
    }
}
