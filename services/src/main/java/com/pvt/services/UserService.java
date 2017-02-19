package com.pvt.services;


import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;

import java.util.List;

public interface UserService<T extends User> extends EntityService<T> {

    List<T> getAllClients() throws ServiceException;

    List<T> getPageOfClients(int pageNumber, int clientsPerPage) throws ServiceException;

    boolean checkUserAuthentication(String login, String password) throws ServiceException;

    T getUserByLogin(String login) throws ServiceException;

    boolean checkIsNewUser(T user) throws ServiceException;

    int getNumberOfPagesWithClients(int clientsPerPage) throws ServiceException;
}
