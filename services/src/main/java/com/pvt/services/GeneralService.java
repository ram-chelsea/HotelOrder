package com.pvt.services;


import com.pvt.entities.Entity;

import java.sql.Connection;
import java.sql.Date;

public abstract class GeneralService<T extends Entity> implements Service<T> {
    protected static final String transactionFailedMessage = "Transaction failed";
    protected Connection connection;

    public static int getDatesDifferenceInDays(Date from, Date till) {
        final int MILLIS_A_DAY = 24 * 60 * 60 * 1000;
        return (int) ((till.getTime() - from.getTime()) / MILLIS_A_DAY);
    }
}
