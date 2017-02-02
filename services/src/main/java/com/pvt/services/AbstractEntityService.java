package com.pvt.services;


import com.pvt.entities.Entity;

import java.sql.Date;

public abstract class AbstractEntityService<T extends Entity> implements EntityService<T> {
    protected static final String transactionFailedMessage = "Transaction failed: ";

    protected static int getDatesDifferenceInDays(Date from, Date till) {
        final int MILLIS_A_DAY = 24 * 60 * 60 * 1000;
        return (int) ((till.getTime() - from.getTime()) / MILLIS_A_DAY);
    }
}
