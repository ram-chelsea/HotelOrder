package com.pvt.service.impl;

import com.pvt.constants.SqlRequest;
import com.pvt.constants.UserRole;
import com.pvt.entities.User;
import com.pvt.exceptions.ServiceException;
import com.pvt.service.EntityServiceImplTest;
import com.pvt.services.impl.UserServiceImpl;
import com.pvt.util.EntityBuilder;
import com.pvt.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImplTest extends EntityServiceImplTest {
    private HibernateUtil util = HibernateUtil.getHibernateUtil();

    @Before
    public void BeforeTest() {
        util.openSession();
    }

    @After
    public void AfterTestCleanDB() {
        Session session = util.getSession();
        Query query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_FALSE);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_USERS);
        query.executeUpdate();
        query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_TRUE);
        query.executeUpdate();
        session.close();
    }

    @Test
    public void testGetInstance() {
        UserServiceImpl firstImpl = UserServiceImpl.getInstance();
        UserServiceImpl secondImpl = UserServiceImpl.getInstance();
        Assert.assertEquals(firstImpl, secondImpl);
    }

    @Test
    public void testAdd() throws ServiceException {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserServiceImpl.getInstance().add(expected);
        User actual = (User) util.getSession().get(User.class, expected.getUserId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws ServiceException {
        List<User> userListExpected = new ArrayList<>();
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        userListExpected.add(user1);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        userListExpected.add(user2);
        User user3 = EntityBuilder.buildUser(null, "TEST_LOGIN_3", "TEST_FIRST_NAME_3", "TEST_LAST_NAME_3", "TEST_PASSWORD_3", UserRole.CLIENT);
        userListExpected.add(user3);
        for (User onlyClient : userListExpected) {
            util.getSession().save(onlyClient);
        }
        User user4 = EntityBuilder.buildUser(null, "TEST_LOGIN_4", "TEST_FIRST_NAME_4", "TEST_LAST_NAME_4", "TEST_PASSWORD_4", UserRole.ADMIN);
        util.getSession().save(user4);
        util.getSession().flush();
        List<User> userListActual = UserServiceImpl.getInstance().getAll();
        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
    }

    @Test
    public void testCheckUserAuthenticationTrue() throws ServiceException {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(user1);
        util.getSession().flush();
        boolean isSiqnedUp = UserServiceImpl.getInstance().checkUserAuthentication(user2.getLogin(), user2.getPassword());
        Assert.assertTrue(isSiqnedUp);
    }

    @Test
    public void testCheckUserAuthenticationFalse() throws ServiceException {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user1);
        util.getSession().flush();
        boolean isSiqnedUp = UserServiceImpl.getInstance().checkUserAuthentication(user2.getLogin(), user2.getPassword());
        Assert.assertFalse(isSiqnedUp);
    }

    @Test
    public void testGetUserByLogin() throws ServiceException {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User falseUser = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(expected);
        util.getSession().save(falseUser);
        util.getSession().flush();
        User actual = UserServiceImpl.getInstance().getUserByLogin(expected.getLogin());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCheckIsNewUserTrue() throws ServiceException {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user1);
        util.getSession().flush();
        boolean isNew = UserServiceImpl.getInstance().checkIsNewUser(user2);
        Assert.assertTrue(isNew);
    }

    @Test
    public void testCheckIsNewUserFalse() throws ServiceException {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        util.getSession().save(user1);
        boolean isNew = UserServiceImpl.getInstance().checkIsNewUser(user2);
        Assert.assertFalse(isNew);
    }

}
