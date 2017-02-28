package com.pvtoc.util;

import com.pvtoc.constants.OrderStatus;
import com.pvtoc.constants.RoomClass;
import com.pvtoc.constants.UserRole;
import com.pvtoc.dto.CreditCardAddingForm;
import com.pvtoc.dto.OrderRoomForm;
import com.pvtoc.dto.RoomAddingForm;
import com.pvtoc.dto.UserRegistrationForm;
import com.pvtoc.entities.CreditCard;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.Room;
import com.pvtoc.entities.User;

import java.sql.Date;

/**
 * Class being used for creating <tt>Entity</tt> object with passed parameters or with passed DTO object
 */
public class EntityBuilder {
    private static final int MILLIS_A_DAY = 24 * 60 * 60 * 1000;
    private static final OrderStatus NEW_ORDER_STATUS = OrderStatus.REQUESTED;
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
    public static User buildUser(UserRegistrationForm userRegistrationForm) {
        User user = new User();
        user.setLogin(userRegistrationForm.getLogin());
        user.setFirstName(userRegistrationForm.getFirstName());
        user.setLastName(userRegistrationForm.getLastName());
        user.setPassword(userRegistrationForm.getPassword());
        user.setUserRole(userRegistrationForm.getUserRole());
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

    public static Order buildOrder(User user, Room room, OrderRoomForm orderRoomForm) {
        Order order = new Order();
        order.setUser(user);
        order.setRoom(room);
        order.setCheckInDate(orderRoomForm.getCheckIn());
        order.setCheckOutDate(orderRoomForm.getCheckOut());
        order.setOrderStatus(NEW_ORDER_STATUS);
        int totalPrice = computeTotalPriceForRoom(room, orderRoomForm.getCheckIn(), orderRoomForm.getCheckOut());
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

    public static Room buildRoom(RoomAddingForm roomAddingForm) {
        Room room = new Room();
        room.setRoomNumber(roomAddingForm.getRoomNumber());
        room.setRoominess(roomAddingForm.getRoominess());
        room.setRoomClass(roomAddingForm.getRoomClass());
        room.setPrice(roomAddingForm.getRoomPrice());
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
    public static CreditCard buildCreditCard(CreditCardAddingForm cardAddingForm) {
        CreditCard card = new CreditCard();
        card.setCardNumber(cardAddingForm.getCardNumber());
        card.setIsValid(cardAddingForm.getIsValid());
        card.setAmount(cardAddingForm.getAmount());
        return card;
    }
    public static int computeTotalPriceForRoom(Room room, Date from, Date till) {
        return getDatesDifferenceInDays(from, till) * room.getPrice();
    }
    public static int getDatesDifferenceInDays(Date from, Date till) {
        return ( int ) ((till.getTime() - from.getTime()) / MILLIS_A_DAY);
    }
}
