package com.pvt.commands.impl.client;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.Order;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowClientOrdersCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private PagesConfigurationManager pagesConfigManagerInst = PagesConfigurationManager.getInstance();
    private OrderServiceImpl orderServiceInst = OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {
            try {
                int userId = user.getUserId();
                util.openSession();
                List<Order> confirmedClientOrdersList = orderServiceInst.getClientOrdersListByStatus(OrderStatus.CONFIRMED, userId);
                List<Order> requestedClientOrdersList = orderServiceInst.getClientOrdersListByStatus(OrderStatus.REQUESTED, userId);
                List<Order> deniedClientOrdersList = orderServiceInst.getClientOrdersListByStatus(OrderStatus.DENIED, userId);
                List<Order> paidClientOrdersList = orderServiceInst.getClientOrdersListByStatus(OrderStatus.ORDERED, userId);
                List<Order> completedClientOrdersList = orderServiceInst.getClientOrdersListByStatus(OrderStatus.COMPLETED, userId);
                util.getSession().close();
                request.setAttribute(Parameters.CLIENT_CONFIRMED_ORDERS_LIST, confirmedClientOrdersList);
                request.setAttribute(Parameters.CLIENT_REQUESTED_ORDERS_LIST, requestedClientOrdersList);
                request.setAttribute(Parameters.CLIENT_DENIED_ORDERS_LIST, deniedClientOrdersList);
                request.setAttribute(Parameters.CLIENT_PAID_ORDERS_LIST, paidClientOrdersList);
                request.setAttribute(Parameters.CLIENT_COMPLETED_ORDERS_LIST, completedClientOrdersList);
                page = pagesConfigManagerInst.getProperty(PagesPaths.CLIENT_SHOW_ORDERS_PAGE);
            } catch (ServiceException e) {
                page = pagesConfigManagerInst.getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, messageManagerInst.getProperty(MessageConstants.ERROR_DATABASE));
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, messageManagerInst.getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }
        return page;
    }
}
//TODO pagination