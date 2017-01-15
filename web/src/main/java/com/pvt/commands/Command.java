package com.pvt.commands;


import javax.servlet.http.HttpServletRequest;


public interface Command {
    String execute(HttpServletRequest request);
}
