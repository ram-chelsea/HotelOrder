package com.pvt.managers;

import com.pvt.constants.ConfigConstants;

import java.util.ResourceBundle;

public class ValidationManager {
    private static ValidationManager instance;
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstants.VALIDATION_SOURCE);

    private ValidationManager() {
    }

    public static synchronized ValidationManager getInstance() {
        if (instance == null) {
            instance = new ValidationManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
