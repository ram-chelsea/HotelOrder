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
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class AddCreditCardCommand implements Command {
    CreditCard card;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {
            try {
                card = RequestParameterParser.getNewCreditCard(request);
                if (areFieldsFullStocked()) {
                    if (areValuesCorrect()) {
                        if (CreditCardServiceImpl.getInstance().isNewCreditCard(card)) {
                            CreditCardServiceImpl.getInstance().add(card);
                            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                        } else {
                            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.CARD_EXISTS));
                        }
                    } else {
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_CARD_VALUES));
                    }
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_FIELDS));
                }
                page = CommandType.GOTOADDCREDITCARD.getCurrentCommand().execute(request);
            } catch (ServiceException | SQLException e) {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            } catch (NumberFormatException e) {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NONSTRING_FORMAT));
                page = CommandType.GOTOADDCREDITCARD.getCurrentCommand().execute(request);
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }
        return page;
    }

    private boolean areFieldsFullStocked() {

        boolean isFullStocked = false;
        if (StringUtils.isNotEmpty(card.getCardNumber())
                & !Integer.valueOf(card.getAmount()).toString().isEmpty()) {
            isFullStocked = true;
        }
        return isFullStocked;
    }

    private boolean areValuesCorrect() {
        boolean areCorrect = false;
        if (card.getAmount() > 0
                & StringUtils.isNumeric(card.getCardNumber())) {
            areCorrect = true;
        }
        return areCorrect;
    }
}
