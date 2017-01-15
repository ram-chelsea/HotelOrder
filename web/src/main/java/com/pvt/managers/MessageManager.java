package com.pvt.managers;

import com.pvt.constants.ConfigConstants;

import java.util.ResourceBundle;

public class MessageManager {
    private static MessageManager instance;
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstants.MESSAGES_SOURCE);

    private MessageManager() {
    }

    public static synchronized MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
