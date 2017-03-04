package com.pvtoc.controllers;

import com.pvtoc.constants.*;
import com.pvtoc.dto.RoomAddingForm;
import com.pvtoc.entities.Order;
import com.pvtoc.entities.Room;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.managers.Manager;
import com.pvtoc.services.OrderService;
import com.pvtoc.services.RoomService;
import com.pvtoc.services.UserService;
import com.pvtoc.util.EntityBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private static final String DEFAULT_NUMBER_PER_PAGE = "10";
    private static final String DEFAULT_CURRENT_PAGE_NUMBER = "1";
    private static final String DEFAULT_ADMIN_SHOW_LIST_OF_ORDER_STATUS = "REQUESTED";
    @Autowired
    private UserService<User> userService;
    @Autowired
    private OrderService<Order> orderService;
    @Autowired
    private RoomService<Room> roomService;
    @Autowired
    @Qualifier("validationManager")
    private Manager validationManager;
    @Autowired
    @Qualifier("messageManager")
    private Manager messageManager;

    //TODO show messages
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String goToAdminStartPage(Model model) throws ServletException, IOException, ServiceException {
        model.addAttribute(Parameters.USER, userService.getUserByLogin(getPrincipalLogin()));
        return "admin/startpage";
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String showClients(@RequestParam(value = Parameters.CURRENT_PAGE, required = false, defaultValue = DEFAULT_CURRENT_PAGE_NUMBER) int currentPage,
                              @RequestParam(value = Parameters.CLIENTS_PER_PAGE, required = false, defaultValue = DEFAULT_NUMBER_PER_PAGE) int clientsPerPage,
                              Model model) throws ServletException, IOException, ServiceException {
        int numberOfPages = userService.getNumberOfPagesWithClients(clientsPerPage);
        List<User> userList = userService.getPageOfClients(currentPage, clientsPerPage);
        List<Integer> perPageNumbersList = PaginationConstants.NUMBER_PER_PAGE_LIST;
        model.addAttribute(Parameters.CLIENTS_PER_PAGE, clientsPerPage);
        model.addAttribute(Parameters.CURRENT_PAGE, currentPage);
        model.addAttribute(Parameters.USER_LIST, userList);
        model.addAttribute(Parameters.PER_PAGE_NUMBERS_LIST, perPageNumbersList);
        model.addAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);
        return "admin/clients";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String showOrders(@RequestParam(value = Parameters.ORDER_STATUS, required = false, defaultValue = DEFAULT_ADMIN_SHOW_LIST_OF_ORDER_STATUS) OrderStatus orderStatus,
                             Model model) throws ServletException, IOException, ServiceException {
        List<Order> ordersList = orderService.getOrdersListByStatus(orderStatus);
        ArrayList orderStatusesList = OrderStatus.enumToList();
        model.addAttribute(Parameters.ORDER_STATUSES_LIST, orderStatusesList);
        model.addAttribute(Parameters.ORDERS_LIST, ordersList);
        model.addAttribute(Parameters.ORDER_STATUS, orderStatus);
        return "admin/orders";
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public String showRooms(@RequestParam(value = Parameters.CURRENT_PAGE, required = false, defaultValue = DEFAULT_CURRENT_PAGE_NUMBER) int currentPage,
                            @RequestParam(value = Parameters.ROOMS_PER_PAGE, required = false, defaultValue = DEFAULT_NUMBER_PER_PAGE) int roomsPerPage,
                            @RequestParam(value = Parameters.OPERATION_MESSAGE, required = false) String message,
                            Model model) throws ServletException, IOException, ServiceException {
        List<Room> roomsList = roomService.getPageOfRooms(currentPage, roomsPerPage);
        List<Integer> perPageNumbersList = PaginationConstants.NUMBER_PER_PAGE_LIST;
        int numberOfPages = roomService.getNumberOfPagesWithRooms(roomsPerPage);
        model.addAttribute(Parameters.OPERATION_MESSAGE, message);
        model.addAttribute(Parameters.PER_PAGE_NUMBERS_LIST, perPageNumbersList);
        model.addAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);
        model.addAttribute(Parameters.ROOMS_PER_PAGE, roomsPerPage);
        model.addAttribute(Parameters.CURRENT_PAGE, currentPage);
        model.addAttribute(Parameters.ROOMS_LIST, roomsList);
        return "admin/rooms";
    }

    @RequestMapping(value = "/rooms/roomprice", method = RequestMethod.GET)
    public String goToChangeRoomPrice(@RequestParam(value = Parameters.ROOM_ID) int roomId,
                                      @RequestParam(value = Parameters.OPERATION_MESSAGE, required = false) String message,
                                      Model model) throws ServletException, IOException, ServiceException {
        Room room = roomService.get(Room.class, roomId);
        model.addAttribute(Parameters.OPERATION_MESSAGE, message);
        model.addAttribute(Parameters.ROOM, room);
        model.addAttribute(Parameters.ROOM_NEW_PRICE_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.ROOM_NEW_PRICE_INPUT_PLACEHOLDER));
        try {
            model.addAttribute(Parameters.ROOM_MIN_NEW_PRICE, Integer.valueOf(validationManager.getProperty(ValidationConstants.ROOM_MIN_NEW_PRICE)));
            model.addAttribute(Parameters.ROOM_NEW_PRICE_STEP, Integer.valueOf(validationManager.getProperty(ValidationConstants.ROOM_NEW_PRICE_STEP)));
        } catch (NumberFormatException e) {
            model.addAttribute(Parameters.FORM_SETTINGS_ERROR, messageManager.getProperty(MessageConstants.FORM_SETTINGS_ERROR));
        }
        return "admin/roomprice";
    }

    @RequestMapping(value = "/rooms/roomprice", method = RequestMethod.POST)
    public String changeRoomPrice(@RequestParam(value = Parameters.ROOM_ID) int roomId,
                                  @RequestParam(value = Parameters.NEW_ROOM_PRICE) int newPrice,
                                  Model model) throws ServletException, IOException, ServiceException {
        if (!Integer.valueOf(newPrice).toString().isEmpty()) {
            if (isNewPriceCorrect(newPrice)) {
                roomService.updateRoomPrice(roomId, newPrice);
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.ROOM_PRICE_CHANGED));
                return "redirect:/admin/rooms";
            } else {
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.INVALID_PRICE));
                return "redirect:/admin/rooms/roomprice";
            }
        } else {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.EMPTY_FIELDS));
            return "redirect:/admin/rooms/roomprice";
        }
    }

    @RequestMapping(value = "/rooms/newroom", method = RequestMethod.GET)
    public String goToAddNewRoom(Model model) throws ServletException, IOException, ServiceException {
        ArrayList roomsClassesList = RoomClass.enumToList();
        model.addAttribute(Parameters.ROOMS_CLASSES_LIST, roomsClassesList);
        model.addAttribute(Parameters.NEW_ROOM_NUMBER_FORMAT_REGEXP, validationManager.getProperty(ValidationConstants.NEW_ROOM_NUMBER_FORMAT_REGEXP));
        model.addAttribute(Parameters.NEW_ROOM_NUMBER_FORMAT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.NEW_ROOM_NUMBER_FORMAT_PLACEHOLDER));
        model.addAttribute(Parameters.AMOUNT_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.AMOUNT_INPUT_PLACEHOLDER));
        try {
            model.addAttribute(Parameters.NEW_ROOM_MIN_ROOMINESS, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_ROOM_MIN_ROOMINESS)));
            model.addAttribute(Parameters.NEW_ROOM_ROOMINESS_STEP, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_ROOM_ROOMINESS_STEP)));
            model.addAttribute(Parameters.NEW_ROOM_MIN_PRICE, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_ROOM_MIN_PRICE)));
            model.addAttribute(Parameters.NEW_ROOM_PRICE_STEP, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_ROOM_PRICE_STEP)));
        } catch (NumberFormatException e) {
            model.addAttribute(Parameters.FORM_SETTINGS_ERROR, messageManager.getProperty(MessageConstants.FORM_SETTINGS_ERROR));
        }
        return "admin/newroom";
    }

    @RequestMapping(value = "/rooms/newroom", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Model addNewRoom(Model model,
                            @RequestBody RoomAddingForm roomDto) throws ServletException, IOException, ServiceException {
        if (areFieldsFullyStocked(roomDto)) {
            if (areNumericFieldsCorrect(roomDto)) {
                Room room = EntityBuilder.buildRoom(roomDto);
                if (roomService.isNewRoom(room)) {
                    roomService.add(room);
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.ROOM_ADDED));
                } else {
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.ROOM_EXISTS));
                }
            } else {
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.INVALID_ROOM_NUMERIC_FIELD_VALUE));
            }
        } else {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.EMPTY_FIELDS));
        }
        return model;
    }

    @RequestMapping(value = "/orders/changestatus", method = RequestMethod.POST)
    public String changeOrderStatus(@RequestParam(value = Parameters.ORDER_ID) int orderId,
                                    @RequestParam(value = Parameters.NEW_ORDER_STATUS) String newStatus,
                                    Model model) throws ServletException, IOException, ServiceException {
        Order order = orderService.get(Order.class, orderId);
        model.addAttribute(Parameters.ORDER_STATUS, order.getOrderStatus());
        OrderStatus newOrderStatus = getNewOrderStatus(order.getOrderStatus(), newStatus);
        orderService.updateOrderStatus(orderId, newOrderStatus);
        return "redirect:/admin/orders";
    }

    private boolean isNewPriceCorrect(int newPrice) {

        boolean isNewPriceCorrect = false;
        if (newPrice > 0) {
            isNewPriceCorrect = true;
        }
        return isNewPriceCorrect;
    }

    private OrderStatus getNewOrderStatus(OrderStatus previousStatus, String status) {
        switch (status) {
            case "confirm":
                return OrderStatus.CONFIRMED;
            case "deny":
                return OrderStatus.DENIED;
            case "expire":
                return OrderStatus.EXPIRED;
            default:
                return previousStatus;

        }
    }

    private boolean areFieldsFullyStocked(RoomAddingForm form) {
        boolean isFullStocked = false;
        if (StringUtils.isNotEmpty(form.getRoomNumber())
                & form.getRoomClass()!=null
                & form.getRoominess()!=null
                & form.getRoomPrice()!=null) {
            isFullStocked = true;
        }
        return isFullStocked;
    }

    private boolean areNumericFieldsCorrect(RoomAddingForm form) {
        boolean areCorrect = false;
        if (StringUtils.isNumeric(form.getRoomNumber())
                & form.getRoominess() > 0
                & form.getRoomPrice() > 0) {
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