package com.pvt.utils;

import org.apache.log4j.Logger;

public class ProjectLogger {
    private static ProjectLogger instance;

    private ProjectLogger() {
    }

    public static synchronized ProjectLogger getInstance() {
        if (instance == null) {
            instance = new ProjectLogger();
        }
        return instance;
    }

    public void logError(Class sender, String message) {
        Logger logger = Logger.getLogger(sender);
        logger.error(message);
    }


}