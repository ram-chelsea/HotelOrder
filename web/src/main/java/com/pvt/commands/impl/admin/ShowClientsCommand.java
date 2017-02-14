package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.UserServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowClientsCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private PagesConfigurationManager pagesConfigManagerInst = PagesConfigurationManager.getInstance();
    private UserServiceImpl userServiceInst = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.ADMIN.equals(user.getUserRole())) {
            try {
                int clientsPerPage = RequestParameterParser.getClientsPerPage(request);
                int currentPage = RequestParameterParser.getCurrentPageNumber(request);
                util.openSession();
                int numberOfPages = userServiceInst.getNumberOfPagesWithClients(clientsPerPage);
                List<User> userList = userServiceInst.getPageOfClients(currentPage, clientsPerPage);
                util.getSession().close();
                List<Integer> perPageNumbersList = PaginationConstants.NUMBER_PER_PAGE_LIST;
                request.setAttribute(Parameters.PER_PAGE_NUMBERS_LIST, perPageNumbersList);
                request.setAttribute(Parameters.USER_LIST, userList);
                request.setAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);
                session.setAttribute(Parameters.CLIENTS_PER_PAGE, clientsPerPage);
                request.setAttribute(Parameters.CURRENT_PAGE, currentPage);
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
