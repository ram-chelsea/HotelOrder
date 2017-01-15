package com.pvt.commands.factory;

import com.pvt.commands.Command;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static CommandFactory instance;

    private CommandFactory() {
    }

    public static synchronized CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public Command parseCommand(HttpServletRequest request) throws IllegalArgumentException {
        Command command;
        try {
            CommandType commandType = RequestParameterParser.getCommandType(request);
            command = commandType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            command = CommandType.LOGIN.getCurrentCommand();
        }
        return command;
    }
}

