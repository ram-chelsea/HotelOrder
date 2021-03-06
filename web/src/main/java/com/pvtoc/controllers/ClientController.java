package com.pvtoc.controllers;

import com.pvtoc.constants.*;
import com.pvtoc.dto.CreditCardAddingForm;
import com.pvtoc.dto.OrderRoomForm;
import com.pvtoc.entities.CreditCard;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.Room;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.managers.Manager;
import com.pvtoc.services.*;
import com.pvtoc.util.EntityBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping(value = "/client")
@SessionAttributes(types = OrderRoomForm.class)
public class ClientController {
    private static final int MILLIS_A_DAY = 24 * 60 * 60 * 1000;
    private final String DEFAULT_CLIENT_SHOW_LIST_OF_ORDER_STATUS = "CONFIRMED";
    @Autowired
    @Qualifier("validationManager")
    private Manager validationManager;
    @Autowired
    @Qualifier("messageManager")
    private Manager messageManager;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private OrderService<Order> orderService;
    @Autowired
    private RoomService<Room> roomService;
    @Autowired
    private CreditCardService<CreditCard> creditCardService;
    @Autowired
    private PayOrderService<CreditCard, Order> payOrderService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String goToClientStartPage(Model model) throws ServletException, IOException, ServiceException {
        model.addAttribute(Parameters.USER, userService.getUserByLogin(getPrincipalLogin()));
        return "client/startpage";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String showOrders(@RequestParam(value = Parameters.ORDER_STATUS, required = false, defaultValue = DEFAULT_CLIENT_SHOW_LIST_OF_ORDER_STATUS) OrderStatus orderStatus,
                             @RequestParam(value = Parameters.OPERATION_MESSAGE, required = false) String message,
                             Model model) throws ServletException, IOException, ServiceException {
        User user = userService.getUserByLogin(getPrincipalLogin());
        List<Order> ordersList = orderService.getClientOrdersListByStatus(orderStatus, user);
        ArrayList orderStatusesList = OrderStatus.enumToList();
        model.addAttribute(Parameters.OPERATION_MESSAGE, message);
        model.addAttribute(Parameters.ORDER_STATUSES_LIST, orderStatusesList);
        model.addAttribute(Parameters.ORDERS_LIST, ordersList);
        model.addAttribute(Parameters.ORDER_STATUS, orderStatus);
        return "client/orders";
    }

    @RequestMapping(value = "/orders/changestatus", method = RequestMethod.POST)
    public String changeOrderStatus(@RequestParam(value = Parameters.ORDER_ID) int orderId,
                                    @RequestParam(value = Parameters.NEW_ORDER_STATUS) String newStatus,
                                    Model model) throws ServletException, IOException, ServiceException {
        Order order = orderService.get(Order.class, orderId);
        model.addAttribute(Parameters.ORDER_STATUS, order.getOrderStatus());
        OrderStatus newOrderStatus = getNewOrderStatus(order.getOrderStatus(), newStatus);
        orderService.updateOrderStatus(orderId, newOrderStatus);
        return "redirect:/client/orders";
    }

    @RequestMapping(value = "/orders/gotopay", method = RequestMethod.POST)
    public String goToPayOrder(@RequestParam(value = Parameters.ORDER_ID) int orderId,
                               Model model) throws ServletException, IOException, ServiceException {
        Order order = orderService.get(Order.class, orderId);
        model.addAttribute(Parameters.ORDER, order);
        try {
            model.addAttribute(Parameters.CARD_NUMBER_FORMAT_REGEXP, validationManager.getProperty(ValidationConstants.CARD_NUMBER_FORMAT_REGEXP));
            model.addAttribute(Parameters.CARD_NUMBER_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.CARD_NUMBER_INPUT_PLACEHOLDER));
        } catch (NumberFormatException e) {
            model.addAttribute(Parameters.FORM_SETTINGS_ERROR, messageManager.getProperty(MessageConstants.FORM_SETTINGS_ERROR));
        }
        return "/client/pay";
    }

    @RequestMapping(value = "/orders/pay", method = RequestMethod.POST)
    public String payOrder(@RequestParam(value = Parameters.ORDER_ID) int orderId,
                           @RequestParam(value = Parameters.CARD_NUMBER) String cardNumber,
                           Model model) throws ServletException, IOException, ServiceException {
        Order order = orderService.get(Order.class, orderId);
        CreditCard card = creditCardService.getByCardNumber(cardNumber);
        if (!card.getCardNumber().isEmpty()) {
            boolean isEnoughMoney = payOrderService.payOrderWithCreditCard(card, order);
            if (isEnoughMoney) {
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.SUCCESS_PAYMENT));
                model.addAttribute(Parameters.ORDER_STATUS, OrderStatus.PAID);
                return "redirect:/client/orders";
            } else {
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.NOT_ENOUGH_MONEY));
                model.addAttribute(Parameters.ORDER, order);
                model.addAttribute(Parameters.CARD_NUMBER_FORMAT_REGEXP, validationManager.getProperty(ValidationConstants.CARD_NUMBER_FORMAT_REGEXP));
                model.addAttribute(Parameters.CARD_NUMBER_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.CARD_NUMBER_INPUT_PLACEHOLDER));
                return "/client/pay";
            }
        } else {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.EMPTY_FIELDS));
            model.addAttribute(Parameters.ORDER, order);
            model.addAttribute(Parameters.CARD_NUMBER_FORMAT_REGEXP, validationManager.getProperty(ValidationConstants.CARD_NUMBER_FORMAT_REGEXP));
            model.addAttribute(Parameters.CARD_NUMBER_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.CARD_NUMBER_INPUT_PLACEHOLDER));
            return "/client/pay";
        }
    }

    @RequestMapping(value = "/orders/makeorder", method = RequestMethod.GET)
    public String goToMakeOrder(Model model,
                                @RequestParam(value = Parameters.OPERATION_MESSAGE, required = false) String message) throws ServletException, IOException, ServiceException {
        List roominessesList = roomService.getRoominesses();
        ArrayList roomsClassesList = RoomClass.enumToList();
        model.addAttribute(Parameters.OPERATION_MESSAGE, message);
        model.addAttribute(Parameters.ROOMS_CLASSES_LIST, roomsClassesList);
        model.addAttribute(Parameters.ROOMINESSES_LIST, roominessesList);
        Date currentDate = new Date();
        model.addAttribute(Parameters.MIN_CHECK_IN_DATE, new java.sql.Date(currentDate.getTime()));
        model.addAttribute(Parameters.MIN_CHECK_OUT_DATE, new java.sql.Date(currentDate.getTime() + MILLIS_A_DAY));
        return "client/makeorder";
    }

    @RequestMapping(value = "/orders/makeorder", method = RequestMethod.POST)
    public String showSuitedRooms(Model model,
                                  HttpServletRequest request) throws ServletException, IOException, ServiceException {
        OrderRoomForm orderRoomForm = getOrderRoomForm(request);
        request.getSession().setAttribute(Parameters.ORDER_ROOM_FORM, orderRoomForm);
        if (areFieldsFullStocked(orderRoomForm)) {
            if (isRoominessCorrect(orderRoomForm)) {
                if (isCheckInOutDatesOrderCorrect(orderRoomForm)) {
                    List<Room> suitedRoomsList = roomService.getSuitedRooms(orderRoomForm);
                    model.addAttribute(Parameters.SUITED_ROOMS_LIST, suitedRoomsList);
                    model.addAttribute(Parameters.ORDER_ROOM_FORM, orderRoomForm);
                    return "client/requestroom";
                } else {
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.INVALID_DATES_ORDER));
                }
            } else {
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.INVALID_ROOM_NUMERIC_FIELD_VALUE));
            }
        } else {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.EMPTY_FIELDS));
        }
        return "redirect:/client/orders/makeorder";
    }

    @RequestMapping(value = "/orders/requestroom", method = RequestMethod.POST)
    public String addOrder(Model model,
                           @RequestParam int roomId,
                           HttpServletRequest request
    ) throws ServletException, IOException, ServiceException {
        OrderRoomForm orderRoomForm = getOrderRoomForm(request);
        Room room = roomService.get(Room.class, roomId);
        User user = userService.getUserByLogin(getPrincipalLogin());
        Order order = EntityBuilder.buildOrder(user, room, orderRoomForm);
        boolean isFree = orderService.createOrderIfRoomIsFree(order);
        if (isFree) {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.SUCCESS_REQUEST_ROOM));
            model.addAttribute(Parameters.ORDER_STATUS, OrderStatus.REQUESTED);
            return "redirect:/client/orders";
        } else {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.ROOM_WAS_BOOKED));
            return "redirect:/client/orders/makeorder";
        }

    }

    @RequestMapping(value = "/creditcards/checkcard", method = RequestMethod.GET)
    public String goToCheckCard(Model model,
                                @RequestParam(value = Parameters.OPERATION_MESSAGE, required = false) String message) throws ServletException, IOException, ServiceException {
        model.addAttribute(Parameters.OPERATION_MESSAGE, message);
        model.addAttribute(Parameters.CARD_NUMBER_FORMAT_REGEXP, validationManager.getProperty(ValidationConstants.CARD_NUMBER_FORMAT_REGEXP));
        model.addAttribute(Parameters.CARD_NUMBER_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.CARD_NUMBER_INPUT_PLACEHOLDER));
        return "client/lookcardamount";
    }

    @RequestMapping(value = "/creditcards/checkcard", method = RequestMethod.POST)
    public String checkCard(Model model, @RequestParam(value = Parameters.CARD_NUMBER) String cardNumber) throws ServletException, IOException, ServiceException {
        CreditCard card = creditCardService.getByCardNumber(cardNumber);
        if (card == null) {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.CARD_NOT_EXISTS));
            return "redirect:/client/creditcards/checkcard";
        } else {
            model.addAttribute(Parameters.CARD, card);
            return "client/cardamount";
        }
    }

    @RequestMapping(value = "/creditcards/newcard", method = RequestMethod.GET)
    public String goToAddCard(Model model) throws ServletException, IOException, ServiceException {
        model.addAttribute(Parameters.CARD_NUMBER_FORMAT_REGEXP, validationManager.getProperty(ValidationConstants.CARD_NUMBER_FORMAT_REGEXP));
        model.addAttribute(Parameters.CARD_NUMBER_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.CARD_NUMBER_INPUT_PLACEHOLDER));
        model.addAttribute(Parameters.AMOUNT_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.AMOUNT_INPUT_PLACEHOLDER));
        try {
            model.addAttribute(Parameters.NEW_CARD_MIN_AMOUNT, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_CARD_MIN_AMOUNT)));
            model.addAttribute(Parameters.NEW_CARD_AMOUNT_STEP, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_CARD_AMOUNT_STEP)));
        } catch (NumberFormatException e) {
            model.addAttribute(Parameters.FORM_SETTINGS_ERROR, messageManager.getProperty(MessageConstants.FORM_SETTINGS_ERROR));
        }
        return "client/newcreditcard";
    }

    @RequestMapping(value = "/creditcards/newcard", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Model addCard(Model model,
                         @RequestBody CreditCardAddingForm cardDto) throws ServletException, IOException, ServiceException {
        CreditCard card = EntityBuilder.buildCreditCard(cardDto);
        if (areFieldsFullStocked(cardDto)) {
            if (areValuesCorrect(cardDto)) {
                if (creditCardService.isNewCreditCard(card)) {
                    creditCardService.add(card);
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.SUCCESS_CARD_ADDING));
                } else {
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.CARD_EXISTS));
                }
            } else {
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.INVALID_CARD_VALUES));
            }
        } else {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.EMPTY_FIELDS));
        }
        return model;
    }

    private OrderStatus getNewOrderStatus(OrderStatus previousStatus, String status) {
        switch (status) {
            case "cancel":
                return OrderStatus.CANCELLED;
            default:
                return previousStatus;

        }
    }

    private OrderRoomForm getOrderRoomForm(HttpServletRequest request) throws IllegalArgumentException {
        OrderRoomForm orderRoomForm;
        HttpSession session = request.getSession();
        if (session.getAttribute(Parameters.ORDER_ROOM_FORM) != null) {
            orderRoomForm = ( OrderRoomForm ) session.getAttribute(Parameters.ORDER_ROOM_FORM);
        } else {
            java.sql.Date checkIn = java.sql.Date.valueOf(request.getParameter(Parameters.CHECK_IN_DATE));
            java.sql.Date checkOut = java.sql.Date.valueOf(request.getParameter(Parameters.CHECK_OUT_DATE));
            int roominess = Integer.valueOf(request.getParameter(Parameters.ROOMINESS));
            RoomClass roomClass = RoomClass.valueOf(request.getParameter(Parameters.ROOM_CLASS));
            orderRoomForm = new OrderRoomForm(roominess, roomClass, checkIn, checkOut);
        }
        return orderRoomForm;
    }

    private boolean areFieldsFullStocked(OrderRoomForm form) {

        boolean isFullStocked = false;
        if (form.getRoominess() != null
                & form.getRoomClass() != null
                & form.getCheckIn() != null
                & form.getCheckOut() != null) {
            isFullStocked = true;
        }
        return isFullStocked;
    }

    private boolean isRoominessCorrect(OrderRoomForm form) {
        boolean isCorrect = false;
        if (form.getRoominess() > 0
                & form.getRoominess() == Math.floor(form.getRoominess())) {
            isCorrect = true;
        }
        return isCorrect;
    }

    private boolean isCheckInOutDatesOrderCorrect(OrderRoomForm form) {

        boolean isCheckInOutDatesOrderCorrect = false;
        if (form.getCheckIn().before(form.getCheckOut())) {
            isCheckInOutDatesOrderCorrect = true;
        }
        return isCheckInOutDatesOrderCorrect;
    }

    private boolean areFieldsFullStocked(CreditCardAddingForm card) {

        boolean isFullStocked = false;
        if (StringUtils.isNotEmpty(card.getCardNumber())
                & card.getAmount() != null) {
            isFullStocked = true;
        }
        return isFullStocked;
    }

    private boolean areValuesCorrect(CreditCardAddingForm card) {
        boolean areCorrect = false;
        if (card.getAmount() >= Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_CARD_MIN_AMOUNT))
                & card.getAmount() == Math.floor(card.getAmount())
                & StringUtils.isNumeric(card.getCardNumber())) {
            areCorrect = true;
        }
        return areCorrect;
    }

    private String getPrincipalLogin() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = (( UserDetails ) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
