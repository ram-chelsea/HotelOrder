package com.pvt.entities;

import com.pvt.constants.UserRole;

/**
 * Describes <tt>User</tt> entity
 */
public class User extends Entity {


    private int userId;
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private UserRole userRole;

    @Override
    public int hashCode() {
        int result = 1;
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((login == null) ? 0 : login.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((password == null) ? 0 : password.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((userRole == null) ? 0 : userRole.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;

        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (userRole == null) {
            if (other.userRole != null) {
                return false;
            }
        } else if (!userRole.equals(other.userRole)) {
            return false;
        }
        return true;
    }

    /**
     * @return the id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId id to set
     */

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the login
     */

    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */

    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the firstName
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the password
     */

    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the userRole
     */

    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * @param userRole user role to set
     */

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

}
