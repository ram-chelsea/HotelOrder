package com.pvt.controllers;

import com.pvt.dto.UserRegistrationForm;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.exceptions.UserAlreadyExistAuthenticationException;
import com.pvt.services.impl.UserServiceImpl;
import com.pvt.util.EntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class LoginRegistrationController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = {"/", "login", "/index"})
    public String login(Model model) throws ServletException, IOException {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) throws ServletException, IOException {
        model.addAttribute("title", "User Registration Form");
        return "registration";
    }

    @RequestMapping(value = {"/user/register"}, method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ModelAndView registerUser(ModelAndView model, @RequestBody UserRegistrationForm registrationForm)
            throws UserAlreadyExistAuthenticationException, ServiceException {

        User user = EntityBuilder.buildUser(registrationForm);
        if (userService.checkIsNewUser(user)) {
            userService.add(user);
            model.addObject("userRegistrationMessage", "User " + user.getLogin() + " was registrated");
        } else {
            model.addObject("operationMessage", "The User has already existed");
        }
        model.addObject("title", "User Registration Form");

        model.setViewName("registration");
        return model;

    }


}
