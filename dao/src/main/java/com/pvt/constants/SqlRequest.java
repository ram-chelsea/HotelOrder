package com.pvt.constants;

/**
 * Contains constants representing SQL requests being used in the project
 */
public final class SqlRequest {
    public static final String TRUNCATE_TEST_USERS = "TRUNCATE TABLE users";
    public static final String TRUNCATE_TEST_ORDERS = "TRUNCATE TABLE orders";
    public static final String TRUNCATE_TEST_ROOMS = "TRUNCATE TABLE rooms";
    public static final String TRUNCATE_TEST_CARDS = "TRUNCATE TABLE creditcards";

    private SqlRequest() {
    }
}
