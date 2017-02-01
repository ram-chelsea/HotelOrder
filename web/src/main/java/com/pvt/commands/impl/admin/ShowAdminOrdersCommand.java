package com.pvt.commands.impl.admin;

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

public class ShowAdminOrdersCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private OrderServiceImpl orderServiceInst = OrderServiceImpl.getInstance();
    private PagesConfigurationManager pagesConfigManagerInst = PagesConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.ADMIN.equals(user.getUserRole())) {
            try {
                OrderStatus orderStatus = RequestParameterParser.getOrderStatus(request);
                util.openSession();
                List<Order> ordersList = orderServiceInst.getOrdersListByStatus(orderStatus);
                util.getSession().close();
                ArrayList orderStatusesList = OrderStatus.enumToList();
                request.setAttribute(Parameters.ORDER_STATUSES_LIST, orderStatusesList);
                request.setAttribute(Parameters.ORDERS_LIST, ordersList);
                request.setAttribute(Parameters.ORDER_STATUS, orderStatus);
                page = pagesConfigManagerInst.getProperty(PagesPaths.ADMIN_SHOW_ORDERS_PAGE);
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
