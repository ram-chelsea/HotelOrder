package com.pvt.utils;

import com.pvt.commands.factory.CommandType;
import com.pvt.constants.OrderStatus;
import com.pvt.constants.Parameters;
import com.pvt.constants.RoomClass;
import com.pvt.constants.UserRole;
import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.RequestNumericAttributeTransferException;
import com.pvt.exceptions.ServiceException;
import com.pvt.services.impl.RoomServiceImpl;
import com.pvt.util.EntityBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class RequestParameterParser {
    private RequestParameterParser() {
    }

    public static User getUser(HttpServletRequest request) throws IllegalArgumentException {
        HttpSession session = request.getSession();
        Integer userId = null;
        UserRole userRole = null;
        String firstName = request.getParameter(Parameters.USER_FIRST_NAME);
        String lastName = request.getParameter(Parameters.USER_LAST_NAME);
        String login = request.getParameter(Parameters.USER_LOGIN);
        String password = request.getParameter(Parameters.USER_PASSWORD);
        if (session.getAttribute(Parameters.USER) != null) {
            User loggedUser = (User) session.getAttribute(Parameters.USER);
            userRole = loggedUser.getUserRole();
            userId = loggedUser.getUserId();
            login = loggedUser.getLogin();
            password = loggedUser.getPassword();
        }

        User user = EntityBuilder.buildUser(userId, login, firstName, lastName, password, userRole);
        return user;
    }

    public static Order getNewOrder(HttpServletRequest request) throws IllegalArgumentException, ServiceException, RequestNumericAttributeTransferException {
        HttpSession session = request.getSession();
        RoomServiceImpl roomServiceInst = RoomServiceImpl.getInstance();
        int roomId = RequestParameterParser.getRoomId(request);
        Room room = roomServiceInst.getById(roomId);
        User user = (User) session.getAttribute(Parameters.USER);
        Date checkIn = (Date) (session.getAttribute(Parameters.CHECK_IN_DATE));
        Date checkOut = (Date) (session.getAttribute(Parameters.CHECK_OUT_DATE));
        int totalPrice = roomServiceInst.computeTotalPriceForRoom(room, checkIn, checkOut);
        Order order = EntityBuilder.buildOrder(null, user, room, checkIn, checkOut, OrderStatus.REQUESTED, totalPrice);
        return order;
    }

    public static int getRoomId(HttpServletRequest request) throws RequestNumericAttributeTransferException {
        int roomId;
        try {
            roomId = Integer.valueOf(request.getParameter(Parameters.ROOM_ID));
        } catch (NumberFormatException e) {
            String message = "Request Numeric Attribute Transfer Error";
            throw new RequestNumericAttributeTransferException(message, e);
        }
        return roomId;
    }

    public static int getOrderId(HttpServletRequest request) throws RequestNumericAttributeTransferException {
        int orderId;
        try {
            orderId = Integer.valueOf(request.getParameter(Parameters.ORDER_ID));
        } catch (NumberFormatException e) {
            String message = "Request Numeric Attribute Transfer Error";
            throw new RequestNumericAttributeTransferException(message, e);
        }
        return orderId;
    }

    public static CommandType getCommandType(HttpServletRequest request) throws IllegalArgumentException {
        String commandName = request.getParameter(Parameters.COMMAND);
        CommandType commandType = CommandType.LOGIN;
        if (commandName != null) {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        }
        return commandType;
    }

    public static int getNewRoomPrice(HttpServletRequest request) throws NumberFormatException {
        return Integer.valueOf(request.getParameter(Parameters.NEW_ROOM_PRICE));
    }

    public static String getCardNumber(HttpServletRequest request) {
        return request.getParameter(Parameters.CARD_NUMBER);
    }

    public static CreditCard getNewCreditCard(HttpServletRequest request) throws NumberFormatException {
        String cardNumber = request.getParameter(Parameters.CARD_NUMBER);
        int amount = Integer.valueOf(request.getParameter(Parameters.AMOUNT));
        CreditCard card = EntityBuilder.buildCreditCard(null, cardNumber, true, amount);
        return card;
    }

    public static Room getNewRoom(HttpServletRequest request) throws IllegalArgumentException {
        String roomNumber = request.getParameter(Parameters.ROOM_NUMBER);
        int roominess = Integer.valueOf(request.getParameter(Parameters.ROOMINESS));
        RoomClass roomClass = RoomClass.valueOf(request.getParameter(Parameters.ROOM_CLASS));
        int price = Integer.valueOf(request.getParameter(Parameters.ROOM_PRICE));
        Room room = EntityBuilder.buildRoom(null, roomNumber, roominess, roomClass, price);
        return room;
    }

    public static Room getOrderedRoomFormat(HttpServletRequest request) throws IllegalArgumentException {
        int roominess = Integer.valueOf(request.getParameter(Parameters.ROOMINESS));
        RoomClass roomClass = RoomClass.valueOf(request.getParameter(Parameters.ROOM_CLASS));

        Room orderedRoomFormat = EntityBuilder.buildRoom(null, null, roominess, roomClass, null);
        return orderedRoomFormat;
    }

    public static Date getCheckInDate(HttpServletRequest request) throws IllegalArgumentException {
        Date checkIn = Date.valueOf(request.getParameter(Parameters.CHECK_IN_DATE));
        return checkIn;
    }

    public static Date getCheckOutDate(HttpServletRequest request) throws IllegalArgumentException {
        Date checkOut = Date.valueOf(request.getParameter(Parameters.CHECK_OUT_DATE));
        return checkOut;
    }


}
