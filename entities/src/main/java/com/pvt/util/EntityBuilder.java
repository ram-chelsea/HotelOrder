package com.pvt.util;

import com.pvt.constants.OrderStatus;
import com.pvt.constants.RoomClass;
import com.pvt.constants.UserRole;
import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;

import java.sql.Date;

/**
 * Class being used for creating <tt>Entity</tt> object with passed parameters
 */
public class EntityBuilder {
    private EntityBuilder() {
    }

    public static User buildUser(Integer userId, String login, String firstName, String lastName, String password, UserRole userRole) {
        User user = new User();
        user.setUserId(userId);
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setUserRole(userRole);
        return user;
    }

    public static Order buildOrder(Integer orderId, User user, Room room, Date checkInDate, Date checkOutDate, OrderStatus orderStatus, Integer totalPrice) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUser(user);
        order.setRoom(room);
        order.setCheckInDate(checkInDate);
        order.setCheckOutDate(checkOutDate);
        order.setOrderStatus(orderStatus);
        order.setTotalPrice(totalPrice);
        return order;
    }

    public static Room buildRoom(Integer roomId, String roomNumber, Integer roominess, RoomClass roomClass, Integer price) {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setRoomNumber(roomNumber);
        room.setRoominess(roominess);
        room.setRoomClass(roomClass);
        room.setPrice(price);
        return room;
    }

    public static CreditCard buildCreditCard(Integer cardId, String cardNumber, Boolean isValid, Integer amount) {
        CreditCard card = new CreditCard();
        card.setCardId(cardId);
        card.setCardNumber(cardNumber);
        card.setIsValid(isValid);
        card.setAmount(amount);
        return card;
    }


}
