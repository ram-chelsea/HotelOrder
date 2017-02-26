package com.pvtoc.service.impl;

import com.pvtoc.constants.UserRole;
import com.pvtoc.entities.User;
import com.pvtoc.exceptions.ServiceException;
import com.pvtoc.services.UserService;
import com.pvtoc.util.EntityBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/service-test-app-context.xml")
@Transactional
@Rollback
public class UserServiceTest {
    @Autowired
    UserService<User> userService;
    @Autowired
    SessionFactory sessionFactory;

    private Session session;

    @Before
    public void BeforeTest() {
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void testAdd() throws ServiceException {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        userService.add(expected);
        User actual = ( User ) session.get(User.class, expected.getUserId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws ServiceException {
        List<User> userListExpected = new ArrayList<>();
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        userListExpected.add(user1);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        userListExpected.add(user2);
        User user3 = EntityBuilder.buildUser(null, "TEST_LOGIN_3", "TEST_FIRST_NAME_3", "TEST_LAST_NAME_3", "TEST_PASSWORD_3", UserRole.ROLE_CLIENT);
        userListExpected.add(user3);
        for (User onlyClient : userListExpected) {
            session.save(onlyClient);
        }
        User user4 = EntityBuilder.buildUser(null, "TEST_LOGIN_4", "TEST_FIRST_NAME_4", "TEST_LAST_NAME_4", "TEST_PASSWORD_4", UserRole.ROLE_ADMIN);
        session.save(user4);
        session.flush();
        List<User> userListActual = userService.getAllClients();
        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
    }

    @Test
    public void testGetUserByLogin() throws ServiceException {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        User falseUser = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(expected);
        session.save(falseUser);
        session.flush();
        User actual = userService.getUserByLogin(expected.getLogin());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCheckIsNewUserTrue() throws ServiceException {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user1);
        session.flush();
        boolean isNew = userService.checkIsNewUser(user2);
        Assert.assertTrue(isNew);
    }

    @Test
    public void testCheckIsNewUserFalse() throws ServiceException {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        session.save(user1);
        boolean isNew = userService.checkIsNewUser(user2);
        Assert.assertFalse(isNew);
    }

    @Test
    public void testGetPagesOfClients() throws ServiceException {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        session.save(user1);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user2);
        User user3 = EntityBuilder.buildUser(null, "TEST_LOGIN_3", "TEST_FIRST_NAME_3", "TEST_LAST_NAME_3", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        session.save(user3);
        User user4 = EntityBuilder.buildUser(null, "TEST_LOGIN_4", "TEST_FIRST_NAME_4", "TEST_LAST_NAME_4", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user4);
        User user5 = EntityBuilder.buildUser(null, "TEST_LOGIN_5", "TEST_FIRST_NAME_5", "TEST_LAST_NAME_5", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        session.save(user5);
        User user6 = EntityBuilder.buildUser(null, "TEST_LOGIN_6", "TEST_FIRST_NAME_6", "TEST_LAST_NAME_6", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user6);
        User user7 = EntityBuilder.buildUser(null, "TEST_LOGIN_7", "TEST_FIRST_NAME_7", "TEST_LAST_NAME_7", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        session.save(user7);
        User user8 = EntityBuilder.buildUser(null, "TEST_LOGIN_8", "TEST_FIRST_NAME_8", "TEST_LAST_NAME_8", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user8);
        List<User> userListExpected = new ArrayList<>();
        userListExpected.add(user4);
        userListExpected.add(user5);
        userListExpected.add(user6);
        List<User> userListActual = userService.getPageOfClients(2, 3);
        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
    }

    @Test
    public void testGetNumberOfPagesWithClients() throws ServiceException {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        session.save(user1);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user2);
        User user3 = EntityBuilder.buildUser(null, "TEST_LOGIN_3", "TEST_FIRST_NAME_3", "TEST_LAST_NAME_3", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        session.save(user3);
        User user4 = EntityBuilder.buildUser(null, "TEST_LOGIN_4", "TEST_FIRST_NAME_4", "TEST_LAST_NAME_4", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user4);
        User user5 = EntityBuilder.buildUser(null, "TEST_LOGIN_5", "TEST_FIRST_NAME_5", "TEST_LAST_NAME_5", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        session.save(user5);
        int numberOfPages = userService.getNumberOfPagesWithClients(2);
        Assert.assertEquals(3, numberOfPages);
    }
    @Test(expected = UnsupportedOperationException.class)
    public void testIsGetIsUnsupported() throws  ServiceException{
        userService.get(User.class, 1);
    }

}
