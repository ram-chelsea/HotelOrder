package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.RequestNumericAttributeTransferException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.MessageManager;
import com.pvt.managers.ValidationManager;
import com.pvt.services.impl.RoomServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;


public class GoToChangeRoomPriceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.ADMIN).equals(user.getUserRole())) {
            try {
                int roomId = RequestParameterParser.getRoomId(request);
                Room room = RoomServiceImpl.getInstance().getById(roomId);
                request.setAttribute(Parameters.ROOM, room);
                request.setAttribute(Parameters.ROOM_NEW_PRICE_INPUT_PLACEHOLDER, ValidationManager.getInstance().getProperty(ValidationConstants.ROOM_NEW_PRICE_INPUT_PLACEHOLDER));
                try {
                    request.setAttribute(Parameters.ROOM_MIN_NEW_PRICE, Integer.valueOf(ValidationManager.getInstance().getProperty(ValidationConstants.ROOM_MIN_NEW_PRICE)));
                    request.setAttribute(Parameters.ROOM_NEW_PRICE_STEP, Integer.valueOf(ValidationManager.getInstance().getProperty(ValidationConstants.ROOM_NEW_PRICE_STEP)));
                } catch (NumberFormatException e) {
                    request.setAttribute(Parameters.FORM_SETTINGS_ERROR, MessageManager.getInstance().getProperty(MessageConstants.FORM_SETTINGS_ERROR));
                }
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.CHANGE_ROOM_PRICE_PAGE);
            } catch (ServiceException | SQLException | RequestNumericAttributeTransferException e) {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        } else {
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
            request.setAttribute(Parameters.ERROR_USER_ROLE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERROR));
        }
        return page;
    }
}
