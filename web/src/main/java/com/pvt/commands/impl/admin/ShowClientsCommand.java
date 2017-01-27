package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.MessageConstants;
import com.pvt.constants.PagesPaths;
import com.pvt.constants.Parameters;
import com.pvt.constants.UserRole;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowClientsCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private PagesConfigurationManager pagesConfigManagerInst = PagesConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.ADMIN.equals(user.getUserRole())) {
            try {
                List<User> userList = UserServiceImpl.getInstance().getAll();
                request.setAttribute(Parameters.USER_LIST, userList);
                page = pagesConfigManagerInst.getProperty(PagesPaths.ADMIN_SHOW_CLIENTS_PAGE);
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
