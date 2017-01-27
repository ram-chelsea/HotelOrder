package com.pvt.commands;


import com.pvt.util.HibernateUtil;

import javax.servlet.http.HttpServletRequest;


public interface Command {
    HibernateUtil util = HibernateUtil.getHibernateUtil();
    String execute(HttpServletRequest request);
}
