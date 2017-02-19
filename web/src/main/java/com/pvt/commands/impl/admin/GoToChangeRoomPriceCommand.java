package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.RequestNumericAttributeTransferException;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.ValidationManager;
import com.pvt.services.impl.RoomServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class GoToChangeRoomPriceCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private ValidationManager validationManagerInst = ValidationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.ADMIN).equals(user.getUserRole())) {
            try {
                int roomId = RequestParameterParser.getRoomId(request);
                util.openSession();
                Room room = RoomServiceImpl.getInstance().get(Room.class, roomId);
                util.getSession().close();
                request.setAttribute(Parameters.ROOM, room);
                request.setAttribute(Parameters.ROOM_NEW_PRICE_INPUT_PLACEHOLDER, validationManagerInst.getProperty(ValidationConstants.ROOM_NEW_PRICE_INPUT_PLACEHOLDER));
                try {
                    request.setAttribute(Parameters.ROOM_MIN_NEW_PRICE, Integer.valueOf(validationManagerInst.getProperty(ValidationConstants.ROOM_MIN_NEW_PRICE)));
                    request.setAttribute(Parameters.ROOM_NEW_PRICE_STEP, Integer.valueOf(validationManagerInst.getProperty(ValidationConstants.ROOM_NEW_PRICE_STEP)));
                } catch (NumberFormatException e) {
                    request.setAttribute(Parameters.FORM_SETTINGS_ERROR, messageManagerInst.getProperty(MessageConstants.FORM_SETTINGS_ERROR));
                }
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.CHANGE_ROOM_PRICE_PAGE);
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
