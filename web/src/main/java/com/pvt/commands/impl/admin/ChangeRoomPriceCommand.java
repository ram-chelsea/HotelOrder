package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.MessageConstants;
import com.pvt.constants.PagesPaths;
import com.pvt.constants.Parameters;
import com.pvt.constants.UserRole;
import com.pvt.entities.User;
import com.pvt.exceptions.RequestNumericAttributeTransferException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.RoomServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeRoomPriceCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.ADMIN.equals(user.getUserRole())) {
            try {
                int roomId = RequestParameterParser.getRoomId(request);
                int newPrice = RequestParameterParser.getNewRoomPrice(request);
                if (!Integer.valueOf(newPrice).toString().isEmpty()) {
                    if (isNewPriceCorrect(newPrice)) {
                        RoomServiceImpl.getInstance().updateRoomPrice(roomId, newPrice);
                        page = CommandType.ROOMS.getCurrentCommand().execute(request);
                    } else {
                        request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.INVALID_PRICE));
                        page = CommandType.GOTOCHANGEROOMPRICE.getCurrentCommand().execute(request);
                    }
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.EMPTY_FIELDS));
                    page = CommandType.GOTOCHANGEROOMPRICE.getCurrentCommand().execute(request);
                }

            } catch (ServiceException | RequestNumericAttributeTransferException e) {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, messageManagerInst.getProperty(MessageConstants.ERROR_DATABASE));
            } catch (NumberFormatException e) {
                request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.INVALID_NONSTRING_FORMAT));
                page = CommandType.GOTOCHANGEROOMPRICE.getCurrentCommand().execute(request);
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, messageManagerInst.getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }
        return page;
    }

    private boolean isNewPriceCorrect(int newPrice) {

        boolean isNewPriceCorrect = false;
        if (newPrice > 0) {
            isNewPriceCorrect = true;
        }
        return isNewPriceCorrect;
    }
}
