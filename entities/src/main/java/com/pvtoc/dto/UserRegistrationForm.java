package com.pvtoc.dto;


import com.pvtoc.constants.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Describes <tt>UserRegistrationForm</tt> DTO object used during registering new <tt>User</tt> object
 */
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
