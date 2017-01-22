package com.pvt.commands.impl.user;


import com.pvt.commands.Command;
import com.pvt.constants.MessageConstants;
import com.pvt.constants.PagesPaths;
import com.pvt.constants.Parameters;
import com.pvt.constants.UserRole;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.MessageManager;
import com.pvt.services.impl.UserServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;


public class LoginUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        try {
            User user = RequestParameterParser.getUser(request);
            if (UserServiceImpl.getInstance().checkUserAuthentication(user.getLogin(), user.getPassword())) {
                user = UserServiceImpl.getInstance().getUserByLogin(user.getLogin());
                session.setAttribute(Parameters.USER, user);
                if (UserRole.CLIENT.equals(user.getUserRole())) {
                    page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.CLIENT_PAGE_PATH);
                } else if (UserRole.ADMIN.equals(user.getUserRole())) {
                    page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ADMIN_PAGE_PATH);
                } else {
                    page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.INDEX_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_USER_ROLE, MessageManager.getInstance().getProperty(MessageConstants.NULL_USER_ROLE));
                }
            } else {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, MessageManager.getInstance().getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        } catch (ServiceException | SQLException e) {
            page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        return page;
    }


}
