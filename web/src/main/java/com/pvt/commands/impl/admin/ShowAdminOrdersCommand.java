package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.Order;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.MessageManager;
import com.pvt.services.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class ShowAdminOrdersCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.ADMIN.equals(user.getUserRole())) {
            try {
                List<Order> requestedOrdersList = OrderServiceImpl.getInstance().getOrdersListByStatus(OrderStatus.REQUESTED);
                List<Order> cancelledOrdersList = OrderServiceImpl.getInstance().getOrdersListByStatus(OrderStatus.CANCELLED);
                List<Order> allPaidOrdersList = OrderServiceImpl.getInstance().getOrdersListByStatus(OrderStatus.ORDERED);
                List<Order> confirmedOrdersList = OrderServiceImpl.getInstance().getOrdersListByStatus(OrderStatus.CONFIRMED);
                List<Order> completedOrdersList = OrderServiceImpl.getInstance().getOrdersListByStatus(OrderStatus.COMPLETED);
                request.setAttribute(Parameters.REQUESTED_ORDERS_LIST, requestedOrdersList);
                request.setAttribute(Parameters.CANCELLED_ORDERS_LIST, cancelledOrdersList);
                request.setAttribute(Parameters.ALL_PAID_ORDERS_LIST, allPaidOrdersList);
                request.setAttribute(Parameters.CONFIRMED_ORDERS_LIST, confirmedOrdersList);
                request.setAttribute(Parameters.COMPLETED_ORDERS_LIST, completedOrdersList);
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ADMIN_SHOW_ORDERS_PAGE);
            } catch (ServiceException | SQLException e) {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
            }
        return page;
    }
}
