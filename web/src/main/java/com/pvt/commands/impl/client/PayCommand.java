package com.pvt.commands.impl.client;


import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.entities.User;
import com.pvt.exceptions.RequestNumericAttributeTransferException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.MessageManager;
import com.pvt.services.impl.CreditCardServiceImpl;
import com.pvt.services.impl.OrderServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class PayCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.CLIENT.equals(user.getUserRole())) {
            try {
                int orderId = RequestParameterParser.getOrderId(request);
                Order order = OrderServiceImpl.getInstance().getById(orderId);
                String cardNumber = RequestParameterParser.getCardNumber(request);
                CreditCard card = CreditCardServiceImpl.getInstance().getByCardNumber(cardNumber);
                if (!card.getCardNumber().isEmpty()) {
                    if (isEnoughMoneyToPayOrder(card, order)) {
                        OrderServiceImpl.getInstance().updateOrderStatus(orderId, OrderStatus.ORDERED);
                        CreditCardServiceImpl.getInstance().takeMoneyForOrder(card, order.getTotalPrice());
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                        page = CommandType.CLIENTORDERS.getCurrentCommand().execute(request);
                    } else {
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.NOT_ENOUGH_MONEY));
                        page = CommandType.GOTOPAY.getCurrentCommand().execute(request);
                    }
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_FIELDS));
                    page = CommandType.GOTOPAY.getCurrentCommand().execute(request);
                }

            } catch (ServiceException | SQLException | RequestNumericAttributeTransferException e) {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }
        return page;
    }

    private boolean isEnoughMoneyToPayOrder(CreditCard card, Order order) {
        boolean isEnoughMoneyToPayOrder = false;
        if (card.getAmount() >= order.getTotalPrice()) {
            isEnoughMoneyToPayOrder = true;
        }
        return isEnoughMoneyToPayOrder;
    }
}
