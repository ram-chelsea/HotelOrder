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
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
                OrderStatus orderStatus = RequestParameterParser.getOrderStatus(request);
                util.openSession();
                List<Order> clientOrdersList = orderServiceInst.getClientOrdersListByStatus(orderStatus, user);
                util.getSession().close();
                ArrayList orderStatusesList = OrderStatus.enumToList();
                request.setAttribute(Parameters.ORDER_STATUSES_LIST, orderStatusesList);
                request.setAttribute(Parameters.ORDERS_LIST, clientOrdersList);
                request.setAttribute(Parameters.ORDER_STATUS, orderStatus);
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
