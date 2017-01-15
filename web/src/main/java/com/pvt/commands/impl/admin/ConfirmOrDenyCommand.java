package com.pvt.commands.impl.admin;

import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandType;
import com.pvt.constants.MessageConstants;
import com.pvt.constants.Parameters;
import com.pvt.constants.UserRole;
import com.pvt.entities.User;
import com.pvt.managers.MessageManager;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ConfirmOrDenyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        if (UserRole.ADMIN.equals(user.getUserRole())) {
            try {
                CommandType commandType = RequestParameterParser.getConfirmOrDenyCommandType(request);
                Command command = commandType.getCurrentCommand();
                page = command.execute(request);
            } catch (IllegalArgumentException e) {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NONSTRING_FORMAT));
                page = CommandType.ADMINORDERS.getCurrentCommand().execute(request);
            }
        } else {
            request.setAttribute(Parameters.ERROR_USER_ROLE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERROR));
            page = CommandType.LOGOUT.getCurrentCommand().execute(request);
            }
        return page;
    }
}
