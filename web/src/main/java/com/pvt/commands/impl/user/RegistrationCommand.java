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
import java.sql.SQLException;

public class RegistrationCommand implements Command {
    private User user;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        try {
            user = RequestParameterParser.getUser(request);
            user.setUserRole(UserRole.CLIENT);
            if (areFieldsFullStocked()) {
                if (UserServiceImpl.getInstance().checkIsNewUser(user)) {
                    UserServiceImpl.getInstance().add(user);
                    page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                } else {
                    page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_USER_EXISTS, MessageManager.getInstance().getProperty(MessageConstants.USER_EXISTS));
                }
            } else {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_FIELDS));
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.REGISTRATION_PAGE_PATH);
            }
        } catch (ServiceException | SQLException e) {
            page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        } catch (IllegalArgumentException e) {
            page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.INDEX_PAGE_PATH);
        }
        return page;
    }


    private boolean areFieldsFullStocked() {

        boolean isFullStocked = false;
        if (!user.getFirstName().isEmpty()
                & !user.getLastName().isEmpty()
                & !user.getLogin().isEmpty()
                & !user.getPassword().isEmpty()) {
            isFullStocked = true;
        }
        return isFullStocked;
    }
}
