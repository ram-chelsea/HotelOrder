package com.pvt.commands.impl.client;


import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.User;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.ValidationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LookCardAmountCommand implements Command {
    private ValidationManager validationManagerInst = ValidationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {
            request.setAttribute(Parameters.CARD_NUMBER_FORMAT_REGEXP, validationManagerInst.getProperty(ValidationConstants.CARD_NUMBER_FORMAT_REGEXP));
            request.setAttribute(Parameters.CARD_NUMBER_INPUT_PLACEHOLDER, validationManagerInst.getProperty(ValidationConstants.CARD_NUMBER_INPUT_PLACEHOLDER));
            page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.LOOK_CARD_AMOUNT_PAGE);
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }
        return page;
    }
}
