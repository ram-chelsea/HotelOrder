package com.pvt.commands.impl.client;

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

public class ShowClientOrdersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {
            try {
                int userId = user.getUserId();
                List<Order> confirmedClientOrdersList = OrderServiceImpl.getInstance().getClientOrdersListByStatus(OrderStatus.CONFIRMED, userId);
                List<Order> requestedClientOrdersList = OrderServiceImpl.getInstance().getClientOrdersListByStatus(OrderStatus.REQUESTED, userId);
                List<Order> deniedClientOrdersList = OrderServiceImpl.getInstance().getClientOrdersListByStatus(OrderStatus.DENIED, userId);
                List<Order> paidClientOrdersList = OrderServiceImpl.getInstance().getClientOrdersListByStatus(OrderStatus.ORDERED, userId);
                List<Order> completedClientOrdersList = OrderServiceImpl.getInstance().getClientOrdersListByStatus(OrderStatus.COMPLETED, userId);
                request.setAttribute(Parameters.CLIENT_CONFIRMED_ORDERS_LIST, confirmedClientOrdersList);
                request.setAttribute(Parameters.CLIENT_REQUESTED_ORDERS_LIST, requestedClientOrdersList);
                request.setAttribute(Parameters.CLIENT_DENIED_ORDERS_LIST, deniedClientOrdersList);
                request.setAttribute(Parameters.CLIENT_PAID_ORDERS_LIST, paidClientOrdersList);
                request.setAttribute(Parameters.CLIENT_COMPLETED_ORDERS_LIST, completedClientOrdersList);
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.CLIENT_SHOW_ORDERS_PAGE);
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
