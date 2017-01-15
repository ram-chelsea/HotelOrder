package com.pvt.managers;

import com.pvt.constants.ConfigConstants;

import java.util.ResourceBundle;

public class PagesConfigurationManager {
    private static PagesConfigurationManager instance;
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstants.PAGES_SOURCE);

    private PagesConfigurationManager() {
    }

    public static synchronized PagesConfigurationManager getInstance() {
        if (instance == null) {
            instance = new PagesConfigurationManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
