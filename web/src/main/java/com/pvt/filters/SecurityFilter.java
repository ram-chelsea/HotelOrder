package com.pvt.filters;

import com.pvt.commands.factory.CommandType;
import com.pvt.constants.PagesPaths;
import com.pvt.constants.UserRole;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.managers.PagesConfigurationManager;
import com.pvt.services.impl.UserServiceImpl;
import com.pvt.utils.RequestParameterParser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class SecurityFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        User user = RequestParameterParser.getUser(httpRequest);
        UserRole userRole = user.getUserRole();
        try {
            CommandType commandType = RequestParameterParser.getCommandType(httpRequest);
            if (userRole == null) {
                if (commandType == CommandType.LOGIN) {
                    chain.doFilter(request, response);
                } else if (commandType == CommandType.GOTOLOGIN) {
                    chain.doFilter(request, response);
                } else if (commandType == CommandType.REGISTRATION) {
                    chain.doFilter(request, response);
                } else if (commandType == CommandType.GOTOREGISTRATION) {
                    chain.doFilter(request, response);
                } else {
                    String page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.INDEX_PAGE_PATH);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                    dispatcher.forward(httpRequest, httpResponse);
                    session.invalidate();
                }
            } else {
                if (UserServiceImpl.getInstance().checkUserAuthentication(user.getLogin(), user.getPassword())) {
                    chain.doFilter(request, response);
                }
                else{
                    String page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.INDEX_PAGE_PATH);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                    dispatcher.forward(httpRequest, httpResponse);
                    session.invalidate();
                }
            }
        } catch (IllegalArgumentException | SQLException |ServiceException e) {
            String page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.INDEX_PAGE_PATH);
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(httpRequest, httpResponse);
        }
    }

    public void destroy() {
    }
}
