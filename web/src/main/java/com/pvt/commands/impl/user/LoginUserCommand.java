package com.pvt.commands.impl.user;


import com.pvt.commands.Command;
import com.pvt.constants.MessageConstants;
import com.pvt.constants.PagesPaths;
import com.pvt.constants.Parameters;
import com.pvt.constants.UserRole;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.UserServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LoginUserCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private PagesConfigurationManager pagesConfigManagerInst = PagesConfigurationManager.getInstance();
    private UserServiceImpl userServiceInst = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        try {
            User user = RequestParameterParser.getUser(request);
            if (userServiceInst.checkUserAuthentication(user.getLogin(), user.getPassword())) {
                user = userServiceInst.getUserByLogin(user.getLogin());
                session.setAttribute(Parameters.USER, user);
                if (UserRole.CLIENT.equals(user.getUserRole())) {
                    page = pagesConfigManagerInst.getProperty(PagesPaths.CLIENT_PAGE_PATH);
                } else if (UserRole.ADMIN.equals(user.getUserRole())) {
                    page = pagesConfigManagerInst.getProperty(PagesPaths.ADMIN_PAGE_PATH);
                } else {
                    page = pagesConfigManagerInst.getProperty(PagesPaths.INDEX_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_USER_ROLE, messageManagerInst.getProperty(MessageConstants.NULL_USER_ROLE));
                }
            } else {
                page = pagesConfigManagerInst.getProperty(PagesPaths.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, messageManagerInst.getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        } catch (ServiceException e) {
            page = pagesConfigManagerInst.getProperty(PagesPaths.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, messageManagerInst.getProperty(MessageConstants.ERROR_DATABASE));
        }
        return page;
    }


}
