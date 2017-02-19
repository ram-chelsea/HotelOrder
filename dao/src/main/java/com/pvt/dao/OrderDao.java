package com.pvt.dao;


import com.pvt.constants.OrderStatus;
import com.pvt.entities.Order;
import com.pvt.entities.User;

import java.util.List;

public interface OrderDao<T extends Order> extends Dao<T> {
    List<T> getOrdersListByStatus(OrderStatus status);

    List<T> getClientOrdersListByStatus(OrderStatus status, User user);

    boolean isFreeRoomForPeriodInOrder(T order);
}
