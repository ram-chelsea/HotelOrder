package com.pvt.services;


import com.pvt.constants.OrderStatus;
import com.pvt.entities.Order;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;

import java.util.List;

public interface OrderService<T extends Order> extends EntityService<T> {

    List<T> getOrdersListByStatus(OrderStatus status) throws ServiceException;

    List<T> getClientOrdersListByStatus(OrderStatus status, User user) throws ServiceException;

    boolean checkIsRoomFreeForPeriodInOrder(T order) throws ServiceException;

    void updateOrderStatus(int orderId, OrderStatus orderStatus) throws ServiceException;

    boolean createOrderIfRoomIsFree(T order) throws ServiceException;
}
