package com.pvt.dao;


import com.pvt.entities.User;

import java.util.List;

public interface UserDao<T extends User> extends Dao<T> {
    List<T> getAllClients();

    List<T> getPageOfClients(int pageNumber, int clientsPerPage);

    T getByLogin(String login);

    boolean isNewUser(String login);

    boolean checkUserAuthentication(String login, String password);

    int getNumberOfPagesWithClients(int clientsPerPage);

}
