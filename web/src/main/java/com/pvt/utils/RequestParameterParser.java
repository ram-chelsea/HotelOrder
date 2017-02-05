package com.pvt.utils;

import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
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
    private static final OrderStatus DEFAULT_ADMIN_SHOW_LIST_OF_ORDER_STATUS = OrderStatus.REQUESTED;
    private static final OrderStatus DEFAULT_CLIENT_SHOW_LIST_OF_ORDER_STATUS = OrderStatus.CONFIRMED;

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

    public static OrderStatus getOrderStatus(HttpServletRequest request) throws IllegalArgumentException {
        HttpSession session = request.getSession();
        OrderStatus orderStatus;
        String orderStatusString;
        orderStatusString = request.getParameter(Parameters.ORDER_STATUS);
        UserRole userRole = ((User) session.getAttribute(Parameters.USER)).getUserRole();
        if (orderStatusString != null) {
            orderStatus = OrderStatus.valueOf(orderStatusString);
        } else {
            if (userRole == UserRole.ADMIN) {
                orderStatus = DEFAULT_ADMIN_SHOW_LIST_OF_ORDER_STATUS;
            } else {
                orderStatus = DEFAULT_CLIENT_SHOW_LIST_OF_ORDER_STATUS;
            }
        }
        return orderStatus;
    }

    public static Integer getClientsPerPage(HttpServletRequest request) throws NumberFormatException {
        HttpSession session = request.getSession();
        String clientsPerPageString = request.getParameter(Parameters.CLIENTS_PER_PAGE);
        Integer sessionClientsPerPage = (Integer) session.getAttribute(Parameters.CLIENTS_PER_PAGE);
        Integer clientsPerPage;
        if (clientsPerPageString != null) {
            clientsPerPage = Integer.valueOf(clientsPerPageString);
        } else {
            if (sessionClientsPerPage != null)
                clientsPerPage = sessionClientsPerPage;
            else {
                clientsPerPage = PaginationConstants.DEFAULT_NUMBER_PER_PAGE;
            }
        }
        return clientsPerPage;
    }

    public static Integer getCurrentPageNumber(HttpServletRequest request) throws NumberFormatException {
        String currentPageString = request.getParameter(Parameters.CURRENT_PAGE);
        Integer currentPage;
        if (currentPageString == null) {
            currentPage = PaginationConstants.DEFAULT_CURRENT_PAGE_NUMBER;
        } else {
            currentPage = Integer.valueOf(currentPageString);
        }
        return currentPage;
    }

    public static Integer getRoomsPerPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String roomsPerPageString = request.getParameter(Parameters.ROOMS_PER_PAGE);
        Integer sessionRoomsPerPage = (Integer) session.getAttribute(Parameters.ROOMS_PER_PAGE);
        Integer roomsPerPage;
        if (roomsPerPageString != null) {
            roomsPerPage = Integer.valueOf(roomsPerPageString);
        } else {
            if (sessionRoomsPerPage != null)
                roomsPerPage = sessionRoomsPerPage;
            else {
                roomsPerPage = PaginationConstants.DEFAULT_NUMBER_PER_PAGE;
            }
        }
        return roomsPerPage;
    }
}
