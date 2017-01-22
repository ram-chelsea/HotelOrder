package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.*;
import com.pvt.entities.User;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.managers.ValidationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class GoToAddNewRoomCommand implements Command {
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private ValidationManager validationManagerInst = ValidationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.ADMIN.equals(user.getUserRole())) {
            ArrayList roomsClassesList = RoomClass.enumToList();
            request.setAttribute(Parameters.ROOMS_CLASSES_LIST, roomsClassesList);
            request.setAttribute(Parameters.NEW_ROOM_NUMBER_FORMAT_REGEXP, validationManagerInst.getProperty(ValidationConstants.NEW_ROOM_NUMBER_FORMAT_REGEXP));
            request.setAttribute(Parameters.NEW_ROOM_NUMBER_FORMAT_PLACEHOLDER, validationManagerInst.getProperty(ValidationConstants.NEW_ROOM_NUMBER_FORMAT_PLACEHOLDER));
            request.setAttribute(Parameters.AMOUNT_INPUT_PLACEHOLDER, validationManagerInst.getProperty(ValidationConstants.AMOUNT_INPUT_PLACEHOLDER));
            try {
                request.setAttribute(Parameters.NEW_ROOM_MIN_ROOMINESS, Integer.valueOf(validationManagerInst.getProperty(ValidationConstants.NEW_ROOM_MIN_ROOMINESS)));
                request.setAttribute(Parameters.NEW_ROOM_ROOMINESS_STEP, Integer.valueOf(validationManagerInst.getProperty(ValidationConstants.NEW_ROOM_ROOMINESS_STEP)));
                request.setAttribute(Parameters.NEW_ROOM_MIN_PRICE, Integer.valueOf(validationManagerInst.getProperty(ValidationConstants.NEW_ROOM_MIN_PRICE)));
                request.setAttribute(Parameters.NEW_ROOM_PRICE_STEP, Integer.valueOf(validationManagerInst.getProperty(ValidationConstants.NEW_ROOM_PRICE_STEP)));
            } catch (NumberFormatException e) {
                request.setAttribute(Parameters.FORM_SETTINGS_ERROR, messageManagerInst.getProperty(MessageConstants.FORM_SETTINGS_ERROR));
            }
            page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ADD_NEW_ROOM_PAGE);

        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, messageManagerInst.getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }

        return page;
    }
}
