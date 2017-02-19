package com.pvt.commands.impl.client;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.Order;
import com.pvt.entities.User;
import com.pvt.exceptions.RequestNumericAttributeTransferException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.ValidationManager;
import com.pvt.services.impl.OrderServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GoToPayCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private ValidationManager validationManagerInst = ValidationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.CLIENT).equals(user.getUserRole())) {
            try {
                int orderId = RequestParameterParser.getOrderId(request);
                util.openSession();
                Order order = OrderServiceImpl.getInstance().get(Order.class, orderId);
                util.getSession().close();
                request.setAttribute(Parameters.ORDER, order);
                request.setAttribute(Parameters.CARD_NUMBER_FORMAT_REGEXP, validationManagerInst.getProperty(ValidationConstants.CARD_NUMBER_FORMAT_REGEXP));
                request.setAttribute(Parameters.CARD_NUMBER_INPUT_PLACEHOLDER, validationManagerInst.getProperty(ValidationConstants.CARD_NUMBER_INPUT_PLACEHOLDER));
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.PAY_PAGE);
            } catch (ServiceException | RequestNumericAttributeTransferException e) {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, messageManagerInst.getProperty(MessageConstants.ERROR_DATABASE));
            }
        } else {
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
            request.setAttribute(Parameters.ERROR_USER_ROLE, messageManagerInst.getProperty(MessageConstants.AUTHORIZATION_ERROR));
        }
        return page;
    }

}
