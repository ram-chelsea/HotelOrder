package com.pvt.commands.impl.client;


import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.MessageConstants;
import com.pvt.constants.PagesPaths;
import com.pvt.constants.Parameters;
import com.pvt.constants.UserRole;
import com.pvt.entities.CreditCard;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.CreditCardServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CardAmountCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private PagesConfigurationManager pagesConfigManagerInst = PagesConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        String cardNumber = RequestParameterParser.getCardNumber(request);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {
            try {
                util.openSession();
                CreditCard card = CreditCardServiceImpl.getInstance().getByCardNumber(cardNumber);
                util.getSession().close();
                if (card == null) {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.CARD_NOT_EXISTS));
                }
                request.setAttribute(Parameters.CARD, card);
                page = pagesConfigManagerInst.getProperty(PagesPaths.CARD_AMOUNT_PAGE);
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
