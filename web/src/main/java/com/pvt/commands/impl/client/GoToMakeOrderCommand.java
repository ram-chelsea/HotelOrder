package com.pvt.commands.impl.client;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.RoomServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoToMakeOrderCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private PagesConfigurationManager pagesConfigManagerInst = PagesConfigurationManager.getInstance();
    private final int MILLIS_A_DAY = 24 * 60 * 60 * 1000;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        util.openSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {
            try {
                util.openSession();
                List roominessesList = RoomServiceImpl.getInstance().getRoominesses();
                util.getSession().close();
                ArrayList roomsClassesList = RoomClass.enumToList();
                request.setAttribute(Parameters.ROOMS_CLASSES_LIST, roomsClassesList);
                request.setAttribute(Parameters.ROOMINESSES_LIST, roominessesList);
                Date currentDate = new Date();
                request.setAttribute(Parameters.MIN_CHECK_IN_DATE, new java.sql.Date(currentDate.getTime()));
                request.setAttribute(Parameters.MIN_CHECK_OUT_DATE, new java.sql.Date(currentDate.getTime() + MILLIS_A_DAY));
                page = pagesConfigManagerInst.getProperty(PagesPaths.MAKE_ORDER_PAGE);
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
