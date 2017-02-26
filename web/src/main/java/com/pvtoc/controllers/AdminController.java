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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping(value = "/admins")
public class AdminController {
    private final String DEFAULT_NUMBER_PER_PAGE = "10";
    private final String DEFAULT_CURRENT_PAGE_NUMBER = "1";
    private final String DEFAULT_ADMIN_SHOW_LIST_OF_ORDER_STATUS = "REQUESTED";
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RoomService roomService;
    @Autowired
    @Qualifier("validationManager")
    private Manager validationManager;

    @RequestMapping(value = {"/{login}"}, method = RequestMethod.GET)
    public String goToAdminStartPage(Model model, @PathVariable(value = "login") String login) throws ServletException, IOException, ServiceException {
        model.addAttribute("title", "User Registration Form");
        model.addAttribute("user", userService.getUserByLogin(login));
        return "admin/startpage";
    }

    @RequestMapping(value = "/{login}/clients", method = RequestMethod.GET)
    public String showClients(@RequestParam(value = "currentPage", required = false, defaultValue = DEFAULT_CURRENT_PAGE_NUMBER) int currentPage,
                              @RequestParam(value = "clientsPerPage", required = false, defaultValue = DEFAULT_NUMBER_PER_PAGE) int clientsPerPage,
                              Model model, @PathVariable(value = "login") String login) throws ServletException, IOException, ServiceException {
        int numberOfPages = userService.getNumberOfPagesWithClients(clientsPerPage);
        List<User> userList = ( List<User> ) userService.getPageOfClients(currentPage, clientsPerPage);
        List<Integer> perPageNumbersList = PaginationConstants.NUMBER_PER_PAGE_LIST;
        model.addAttribute(Parameters.LOGIN, login);
        model.addAttribute(Parameters.CLIENTS_PER_PAGE, clientsPerPage);
        model.addAttribute(Parameters.CURRENT_PAGE, currentPage);
        model.addAttribute(Parameters.USER_LIST, userList);
        model.addAttribute(Parameters.PER_PAGE_NUMBERS_LIST, perPageNumbersList);
        model.addAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);

        return "admin/clients";
    }

    @RequestMapping(value = "/{login}/orders", method = RequestMethod.GET)
    public String showOrders(@RequestParam(value = "orderStatus", required = false, defaultValue = DEFAULT_ADMIN_SHOW_LIST_OF_ORDER_STATUS) OrderStatus orderStatus,
                             Model model, @PathVariable(value = "login") String login) throws ServletException, IOException, ServiceException {
        List<Order> ordersList = orderService.getOrdersListByStatus(orderStatus);
        ArrayList orderStatusesList = OrderStatus.enumToList();
        model.addAttribute(Parameters.LOGIN, login);
        model.addAttribute(Parameters.ORDER_STATUSES_LIST, orderStatusesList);
        model.addAttribute(Parameters.ORDERS_LIST, ordersList);
        model.addAttribute(Parameters.ORDER_STATUS, orderStatus);
        return "admin/orders";
    }

    @RequestMapping(value = "/{login}/rooms", method = RequestMethod.GET)
    public String showRooms(@RequestParam(value = "currentPage", required = false, defaultValue = DEFAULT_CURRENT_PAGE_NUMBER) int currentPage,
                            @RequestParam(value = "roomsPerPage", required = false, defaultValue = DEFAULT_NUMBER_PER_PAGE) int roomsPerPage,
                            Model model, @PathVariable(value = "login") String login) throws ServletException, IOException, ServiceException {
        List<Room> roomsList = roomService.getPageOfRooms(currentPage, roomsPerPage);
        List<Integer> perPageNumbersList = PaginationConstants.NUMBER_PER_PAGE_LIST;
        int numberOfPages = roomService.getNumberOfPagesWithRooms(roomsPerPage);
        model.addAttribute(Parameters.LOGIN, login);
        model.addAttribute(Parameters.PER_PAGE_NUMBERS_LIST, perPageNumbersList);
        model.addAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);
        model.addAttribute(Parameters.ROOMS_PER_PAGE, roomsPerPage);
        model.addAttribute(Parameters.CURRENT_PAGE, currentPage);
        model.addAttribute(Parameters.ROOMS_LIST, roomsList);
        return "admin/rooms";
    }

    @RequestMapping(value = "/{login}/rooms/changeroomprice", method = RequestMethod.GET)
    public String goToChangeRoomPrice(@RequestParam(value = "roomId") int roomId,
                                      Model model, @PathVariable(value = "login") String login) throws ServletException, IOException, ServiceException {
        Room room = ( Room ) roomService.get(Room.class, roomId);
        model.addAttribute(Parameters.LOGIN, login);
        model.addAttribute(Parameters.ROOM, room);
        model.addAttribute(Parameters.ROOM_NEW_PRICE_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.ROOM_NEW_PRICE_INPUT_PLACEHOLDER));
        model.addAttribute(Parameters.ROOM_MIN_NEW_PRICE, Integer.valueOf(validationManager.getProperty(ValidationConstants.ROOM_MIN_NEW_PRICE)));
        model.addAttribute(Parameters.ROOM_NEW_PRICE_STEP, Integer.valueOf(validationManager.getProperty(ValidationConstants.ROOM_NEW_PRICE_STEP)));

        return "admin/changeroomprice";
    }

    @RequestMapping(value = "/{login}/rooms/changeroomprice", method = RequestMethod.POST)//TODO default roomId
    public String changeRoomPrice(@RequestParam(value = "roomId") int roomId,
                                  @RequestParam(value = "newPrice") int newPrice,
                                  Model model, @PathVariable(value = "login") String login) throws ServletException, IOException, ServiceException {
        model.addAttribute(Parameters.LOGIN, login);
        if (!Integer.valueOf(newPrice).toString().isEmpty()) {
            if (isNewPriceCorrect(newPrice)) {
                roomService.updateRoomPrice(roomId, newPrice);
                return "redirect:/admins/{login}/rooms";
            } else {
                return "/";//TODO
            }
        } else {
            return "/";//TODO
        }
    }

    @RequestMapping(value = "/{login}/rooms/addnewroom", method = RequestMethod.GET)
    public String goToAddNewRoom(Model model, @PathVariable(value = "login") String login) throws ServletException, IOException, ServiceException {

        ArrayList roomsClassesList = RoomClass.enumToList();
        model.addAttribute(Parameters.LOGIN, login);
        model.addAttribute(Parameters.ROOMS_CLASSES_LIST, roomsClassesList);
        model.addAttribute(Parameters.NEW_ROOM_NUMBER_FORMAT_REGEXP, validationManager.getProperty(ValidationConstants.NEW_ROOM_NUMBER_FORMAT_REGEXP));
        model.addAttribute(Parameters.NEW_ROOM_NUMBER_FORMAT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.NEW_ROOM_NUMBER_FORMAT_PLACEHOLDER));
        model.addAttribute(Parameters.AMOUNT_INPUT_PLACEHOLDER, validationManager.getProperty(ValidationConstants.AMOUNT_INPUT_PLACEHOLDER));
        model.addAttribute(Parameters.NEW_ROOM_MIN_ROOMINESS, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_ROOM_MIN_ROOMINESS)));
        model.addAttribute(Parameters.NEW_ROOM_ROOMINESS_STEP, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_ROOM_ROOMINESS_STEP)));
        model.addAttribute(Parameters.NEW_ROOM_MIN_PRICE, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_ROOM_MIN_PRICE)));
        model.addAttribute(Parameters.NEW_ROOM_PRICE_STEP, Integer.valueOf(validationManager.getProperty(ValidationConstants.NEW_ROOM_PRICE_STEP)));

        return "admin/addnewroom";
    }

    @RequestMapping(value = "/{login}/rooms/addnewroom", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public
    @ResponseBody
    Model addNewRoom(Model model, @PathVariable(value = "login") String login,
                     @RequestBody RoomAddingForm roomDto) throws ServletException, IOException, ServiceException {
        model.addAttribute(Parameters.LOGIN, login);
        Room room = EntityBuilder.buildRoom(roomDto);
        if (areFieldsFullyStocked(room)) {
            if (areNumericFieldsCorrect(room)) {
                if (roomService.isNewRoom(room)) {
                    roomService.add(room);
                }
            }
        }
        return model;
    }

    @RequestMapping(value = "/{login}/orders/changestatus", method = RequestMethod.POST)
    public String changeOrderStatus(@RequestParam(value = "orderId") int orderId,
                                    @RequestParam(value = "newStatus") String newStatus,
                                    Model model, @PathVariable(value = "login") String login) throws ServletException, IOException, ServiceException {
        Order order = ( Order ) orderService.get(Order.class, orderId);
        model.addAttribute(Parameters.LOGIN, login);
        model.addAttribute(Parameters.ORDER_STATUS, order.getOrderStatus());
        OrderStatus newOrderStatus = getNewOrderStatus(order.getOrderStatus(), newStatus);
        orderService.updateOrderStatus(orderId, newOrderStatus);
        return "redirect:/admins/{login}/orders";
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

    private boolean areFieldsFullyStocked(Room room) {

        boolean isFullStocked = false;
        if (StringUtils.isNotEmpty(room.getRoomNumber())
                & !room.getRoomClass().toString().isEmpty()
                & !Integer.valueOf(room.getRoominess()).toString().isEmpty()
                & !Integer.valueOf(room.getPrice()).toString().isEmpty()) {
            isFullStocked = true;
        }
        return isFullStocked;
    }

    private boolean areNumericFieldsCorrect(Room room) {
        boolean areCorrect = false;
        if (StringUtils.isNumeric(room.getRoomNumber())
                & room.getRoominess() > 0
                & room.getPrice() > 0) {
            areCorrect = true;
        }
        return areCorrect;
    }

}