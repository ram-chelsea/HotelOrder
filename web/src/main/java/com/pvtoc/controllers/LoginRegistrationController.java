package com.pvtoc.controllers;

import com.pvtoc.dto.UserRegistrationForm;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.exceptions.UserAlreadyExistAuthenticationException;
import com.pvtoc.services.UserService;
import com.pvtoc.util.EntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

    @RequestMapping(value = {"/user/register"}, method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public
    @ResponseBody
    Model registerUser(Model model, @RequestBody UserRegistrationForm registrationForm)
            throws UserAlreadyExistAuthenticationException, ServiceException {

        User user = EntityBuilder.buildUser(registrationForm);
        if (userService.checkIsNewUser(user)) {
            userService.add(user);
            model.addAttribute("userRegistrationMessage", "User " + user.getLogin() + " was registrated");
        } else {
            model.addAttribute("operationMessage", "The User has already existed");
        }
        model.addAttribute("title", "User Registration Form");

        return model;

    }

    @RequestMapping(value = {"/", "login"})
    public String login(Model model) throws ServletException, IOException {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) throws ServletException, IOException {
        model.addAttribute("title", "User Registration Form");
        return "registration";
    }


}
