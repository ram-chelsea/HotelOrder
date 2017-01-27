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

public class RegistrationCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private PagesConfigurationManager pagesConfigManagerInst = PagesConfigurationManager.getInstance();
    private UserServiceImpl userServiceInst = UserServiceImpl.getInstance();
    private User user;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        try {
            user = RequestParameterParser.getUser(request);
            user.setUserRole(UserRole.CLIENT);
            if (areFieldsFullStocked()) {
                util.openSession();
                if (userServiceInst.checkIsNewUser(user)) {
                    userServiceInst.add(user);
                    page = pagesConfigManagerInst.getProperty(PagesPaths.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.SUCCESS_OPERATION));
                } else {
                    page = pagesConfigManagerInst.getProperty(PagesPaths.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_USER_EXISTS, messageManagerInst.getProperty(MessageConstants.USER_EXISTS));
                }
                util.getSession().close();
            } else {
                request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.EMPTY_FIELDS));
                page = pagesConfigManagerInst.getProperty(PagesPaths.REGISTRATION_PAGE_PATH);
            }
        } catch (ServiceException e) {
            page = pagesConfigManagerInst.getProperty(PagesPaths.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, messageManagerInst.getProperty(MessageConstants.ERROR_DATABASE));
        } catch (IllegalArgumentException e) {
            page = pagesConfigManagerInst.getProperty(PagesPaths.INDEX_PAGE_PATH);
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
