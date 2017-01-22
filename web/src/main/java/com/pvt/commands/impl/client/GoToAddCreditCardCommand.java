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

public class GoToAddCreditCardCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private ValidationManager validationManagerInst = ValidationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {

            request.setAttribute(Parameters.CARD_NUMBER_FORMAT_REGEXP, validationManagerInst.getProperty(ValidationConstants.CARD_NUMBER_FORMAT_REGEXP));
            request.setAttribute(Parameters.CARD_NUMBER_INPUT_PLACEHOLDER, validationManagerInst.getProperty(ValidationConstants.CARD_NUMBER_INPUT_PLACEHOLDER));
            request.setAttribute(Parameters.AMOUNT_INPUT_PLACEHOLDER, validationManagerInst.getProperty(ValidationConstants.AMOUNT_INPUT_PLACEHOLDER));
            try {
                request.setAttribute(Parameters.NEW_CARD_MIN_AMOUNT, Integer.valueOf(validationManagerInst.getProperty(ValidationConstants.NEW_CARD_MIN_AMOUNT)));
                request.setAttribute(Parameters.NEW_CARD_AMOUNT_STEP, Integer.valueOf(validationManagerInst.getProperty(ValidationConstants.NEW_CARD_AMOUNT_STEP)));
            } catch (NumberFormatException e) {
                request.setAttribute(Parameters.FORM_SETTINGS_ERROR, messageManagerInst.getProperty(MessageConstants.FORM_SETTINGS_ERROR));
            }
            page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ADD_CREDIT_CARD_PAGE);
        } else {
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
            request.setAttribute(Parameters.ERROR_USER_ROLE, messageManagerInst.getProperty(MessageConstants.AUTHORIZATION_ERROR));
        }
        return page;
    }
}
