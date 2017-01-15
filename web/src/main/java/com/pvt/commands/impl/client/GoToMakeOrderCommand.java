package com.pvt.commands.impl.client;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.MessageManager;
import com.pvt.services.impl.RoomServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoToMakeOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {
            try {
                List roominessesList = RoomServiceImpl.getInstance().getRoominesses();
                ArrayList roomsClassesList = RoomClass.enumToList();
                request.setAttribute(Parameters.ROOMS_CLASSES_LIST, roomsClassesList);
                request.setAttribute(Parameters.ROOMINESSES_LIST, roominessesList);
                request.setAttribute(Parameters.MIN_CHECK_IN_DATE, new java.sql.Date(new java.util.Date().getTime()));
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.MAKE_ORDER_PAGE);
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
