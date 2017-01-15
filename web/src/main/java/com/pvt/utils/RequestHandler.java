package com.pvt.utils;


import com.pvt.commands.Command;
import com.pvt.commands.factory.CommandFactory;
import com.pvt.constants.PagesPaths;
import com.pvt.managers.PagesConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestHandler {
    private RequestHandler() {
    }

    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandFactory commandFactory = CommandFactory.getInstance();
        Command сommand = commandFactory.parseCommand(request);
        String page = сommand.execute(request);
        if (page != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.INDEX_PAGE_PATH);
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
