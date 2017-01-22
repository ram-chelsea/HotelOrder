package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.MessageConstants;
import com.pvt.constants.PagesPaths;
import com.pvt.constants.Parameters;
import com.pvt.constants.UserRole;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.MessageManager;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.RoomServiceImpl;
import com.pvt.utils.RequestParameterParser;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class AddNewRoomCommand implements Command {
    private RoomServiceImpl roomServiceInst = RoomServiceImpl.getInstance();
    private MessageManager messageManagerInst = MessageManager.getInstance();
    private Room room;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if ((UserRole.ADMIN).equals(user.getUserRole())) {
            try {
                room = RequestParameterParser.getNewRoom(request);
                if (areFieldsFullyStocked()) {
                    if (areNumericFieldsCorrect()) {
                        if (roomServiceInst.isNewRoom(room)) {
                            roomServiceInst.add(room);
                            request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.SUCCESS_OPERATION));
                        } else {
                            request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.ROOM_EXISTS));
                        }
                    } else {
                        request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.INVALID_ROOM_NUMERIC_FIELD_VALUE));
                    }
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.EMPTY_FIELDS));
                }
                page = CommandType.GOTOADDNEWROOM.getCurrentCommand().execute(request);

            } catch (ServiceException | SQLException e) {
                request.setAttribute(Parameters.ERROR_DATABASE, messageManagerInst.getProperty(MessageConstants.ERROR_DATABASE));
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
            } catch (IllegalArgumentException e) {
                request.setAttribute(Parameters.OPERATION_MESSAGE, messageManagerInst.getProperty(MessageConstants.INVALID_NONSTRING_FORMAT));
                page = CommandType.GOTOADDNEWROOM.getCurrentCommand().execute(request);
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, messageManagerInst.getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }

        return page;
    }

    private boolean areFieldsFullyStocked() {

        boolean isFullStocked = false;
        if (StringUtils.isNotEmpty(room.getRoomNumber())
                & !room.getRoomClass().toString().isEmpty()
                & !Integer.valueOf(room.getRoominess()).toString().isEmpty()
                & !Integer.valueOf(room.getPrice()).toString().isEmpty()) {
            isFullStocked = true;
        }
        return isFullStocked;
    }

    private boolean areNumericFieldsCorrect() {
        boolean areCorrect = false;
        if (StringUtils.isNumeric(room.getRoomNumber())
                & room.getRoominess() > 0
                & room.getPrice() > 0) {
            areCorrect = true;
        }
        return areCorrect;
    }
}
