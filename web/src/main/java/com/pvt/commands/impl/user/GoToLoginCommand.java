package com.pvt.commands.impl.user;

import com.pvt.commands.Command;
import com.pvt.constants.PagesPaths;
import com.pvt.managers.PagesConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToLoginCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        String page = PagesConfigurationManager.getInstance().getProperty(PagesPaths.INDEX_PAGE_PATH);
        return page;
    }
}