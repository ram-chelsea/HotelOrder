package com.pvtoc.controllers;

import com.pvtoc.constants.MessageConstants;
import com.pvtoc.constants.Parameters;
import com.pvtoc.dto.UserRegistrationForm;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.exceptions.UserAlreadyExistAuthenticationException;
import com.pvtoc.managers.Manager;
import com.pvtoc.services.UserService;
import com.pvtoc.util.EntityBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;

@Controller
public class LoginRegistrationController {
    @Autowired
    @Qualifier("messageManager")
    private Manager messageManager;
    @Autowired
    private UserService<User> userService;

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Model registerUser(Model model, @RequestBody UserRegistrationForm registrationForm)
            throws UserAlreadyExistAuthenticationException, ServiceException {
        if (areFieldsFullyStocked(registrationForm)) {
            User user = EntityBuilder.buildUser(registrationForm);
            if (userService.checkIsNewUser(user)) {
                userService.add(user);
                model.addAttribute(Parameters.OPERATION_MESSAGE, "User  " + user.getLogin() + "  was registered");
            } else {
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.USER_EXISTS));
            }
        } else {
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.EMPTY_FIELDS));
        }
        return model;
    }

    @RequestMapping(value = {"/", "login"})
    public String login(Model model) throws ServletException, IOException {
        model.addAttribute(Parameters.TITLE, "Login Page");
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) throws ServletException, IOException {
        model.addAttribute(Parameters.TITLE, "User Registration Form");
        return "registration";
    }

    private boolean areFieldsFullyStocked(UserRegistrationForm form) {
        boolean isFullStocked = false;
        if (form.getUserRole() != null
                & StringUtils.isNotEmpty(form.getFirstName())
                & StringUtils.isNotEmpty(form.getLastName())
                & StringUtils.isNotEmpty(form.getLogin())) {
            isFullStocked = true;
        }
        return isFullStocked;
    }
}
