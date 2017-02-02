package com.pvt.commands.impl.client;


import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.MessageConstants;
import com.pvt.constants.PagesPaths;
import com.pvt.constants.Parameters;
import com.pvt.constants.UserRole;
import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.entities.User;
import com.pvt.exceptions.RequestNumericAttributeTransferException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.PayOrderServiceImpl;
import com.pvt.services.impl.CreditCardServiceImpl;
import com.pvt.services.impl.OrderServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PayCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private OrderServiceImpl orderServiceInst = OrderServiceImpl.getInstance();
    private CreditCardServiceImpl cardServiceInst = CreditCardServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.CLIENT.equals(user.getUserRole())) {
            try {
                int orderId = RequestParameterParser.getOrderId(request);
                util.openSession();
                Order order = orderServiceInst.getById(orderId);
                String cardNumber = RequestParameterParser.getCardNumber(request);
                CreditCard card = cardServiceInst.getByCardNumber(cardNumber);
                if (!card.getCardNumber().isEmpty()) {
                    boolean isEnoughMoney = PayOrderServiceImpl.getInstance().payOrderWithCreditCard(card, order);
                    if (isEnoughMoney) {
                        util.getSession().close();
                        request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.SUCCESS_OPERATION));
                        page = CommandType.CLIENTORDERS.getCurrentCommand().execute(request);
                    } else {
                        util.getSession().close();
                        request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.NOT_ENOUGH_MONEY));
                        page = CommandType.GOTOPAY.getCurrentCommand().execute(request);
                    }

                } else {
                    util.getSession().close();
                    request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.EMPTY_FIELDS));
                    page = CommandType.GOTOPAY.getCurrentCommand().execute(request);
                }
            } catch (ServiceException | RequestNumericAttributeTransferException e) {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, messageManagerInst.getProperty(MessageConstants.ERROR_DATABASE));
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, messageManagerInst.getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }
        return page;
    }
}
