package com.pvt.commands.impl.client;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MakeOrderCommand implements Command {
    private Room orderedRoomFormat;
    private Date checkIn;
    private Date checkOut;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (user.getUserRole().equals(UserRole.CLIENT)) {
            try {
                checkIn = RequestParameterParser.getCheckInDate(request);
                checkOut = RequestParameterParser.getCheckOutDate(request);
                orderedRoomFormat = RequestParameterParser.getOrderedRoomFormat(request);
                if (areFieldsFullStocked()) {
                    if (isRoominessCorrect()) {
                        if (isCheckInOutDatesOrderCorrect(checkIn, checkOut)) {
                            List<Room> suitedRoomsList = RoomServiceImpl.getInstance().getSuitedRooms(orderedRoomFormat, checkIn, checkOut);
                            request.setAttribute(Parameters.SUITED_ROOMS_LIST, suitedRoomsList);
                            session.setAttribute(Parameters.CHECK_IN_DATE, checkIn);
                            session.setAttribute(Parameters.CHECK_OUT_DATE, checkOut);
                            page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ORDER_REQUEST_ROOM_PAGE);
                        } else {
                            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_DATES_ORDER));
                            page = CommandType.GOTOMAKEORDER.getCurrentCommand().execute(request);
                        }
                    } else {
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_ROOM_NUMERIC_FIELD_VALUE));
                        page = CommandType.GOTOMAKEORDER.getCurrentCommand().execute(request);
                    }
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_FIELDS));
                    page = CommandType.GOTOMAKEORDER.getCurrentCommand().execute(request);
                }
            } catch (ServiceException | SQLException e) {
                page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            } catch (IllegalArgumentException e) {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NONSTRING_FORMAT));
                page = CommandType.GOTOMAKEORDER.getCurrentCommand().execute(request);
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
        }

        return page;
    }

    private boolean isCheckInOutDatesOrderCorrect(Date checkIn, Date checkOut) {

        boolean isCheckInOutDatesOrderCorrect = false;
        if (checkIn.before(checkOut)) {
            isCheckInOutDatesOrderCorrect = true;
        }
        return isCheckInOutDatesOrderCorrect;
    }

    private boolean areFieldsFullStocked() {

        boolean isFullStocked = false;
        if (!Integer.valueOf(orderedRoomFormat.getRoominess()).toString().isEmpty()
                & !orderedRoomFormat.getRoomClass().toString().isEmpty()
                & !checkIn.toString().isEmpty()
                & !checkOut.toString().isEmpty()) {
            isFullStocked = true;
        }
        return isFullStocked;
    }

    private boolean isRoominessCorrect() {
        boolean isCorrect = false;
        if (orderedRoomFormat.getRoominess() > 0) {
            isCorrect = true;
        }
        return isCorrect;
    }
}
