package com.pvt.dto;


import com.pvt.constants.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationForm {

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private UserRole userRole;

    public UserRegistrationForm(final String firstName, final String lastName, final String login, final String password, final UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

}
