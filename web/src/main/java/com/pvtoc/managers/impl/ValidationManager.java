package com.pvtoc.managers.impl;

import com.pvtoc.constants.ConfigConstants;
import com.pvtoc.managers.Manager;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
@NoArgsConstructor
public class ValidationManager implements Manager {
    private static final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstants.VALIDATION_SOURCE);

    @Override
    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
