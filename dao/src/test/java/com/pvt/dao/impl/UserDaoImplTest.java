package com.pvt.dao.impl;

import com.pvt.constants.SqlRequest;
import com.pvt.constants.UserRole;
import com.pvt.dao.EntityDaoImplTest;
import com.pvt.entities.User;
import com.pvt.exceptions.DaoException;
import com.pvt.util.EntityBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImplTest extends EntityDaoImplTest {


    @Before
    @After
    public void CleanTestDB() throws DaoException, SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_USERS);

    }

    @Test
    public void testGetInstance() {
        UserDaoImpl firstImpl = UserDaoImpl.getInstance();
        UserDaoImpl secondImpl = UserDaoImpl.getInstance();
        Assert.assertEquals(firstImpl.hashCode(), secondImpl.hashCode());
    }

    @Test
    public void testAdd() throws DaoException {
        User expected = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(expected);
        User actual = UserDaoImpl.getInstance().getByLogin(expected.getLogin());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testGetAll() throws DaoException {
        List<User> userListExpected = new ArrayList<>();
        User user1 = EntityBuilder.buildUser(1, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        userListExpected.add(user1);
        User user2 = EntityBuilder.buildUser(2, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        userListExpected.add(user2);
        User user3 = EntityBuilder.buildUser(3, "TEST_LOGIN_3", "TEST_FIRST_NAME_3", "TEST_LAST_NAME_3", "TEST_PASSWORD_3", UserRole.CLIENT);
        userListExpected.add(user3);
        for (User onlyClient : userListExpected) {
            UserDaoImpl.getInstance().add(onlyClient);
        }
        List<User> userListActual = UserDaoImpl.getInstance().getAll();

        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
    }

    @Test
    public void testGetById() throws DaoException {
        User preUser = EntityBuilder.buildUser(0, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User otherUser = EntityBuilder.buildUser(0, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser);
        UserDaoImpl.getInstance().add(otherUser);
        User expected = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
        User actual = UserDaoImpl.getInstance().getById(expected.getUserId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetByLogin() throws DaoException {
        User expected = EntityBuilder.buildUser(0, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User otherUser = EntityBuilder.buildUser(0, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(expected);
        UserDaoImpl.getInstance().add(otherUser);
        User actual = UserDaoImpl.getInstance().getByLogin(expected.getLogin());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testDelete() throws DaoException {
        User preUser = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(preUser);
        User expected = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
        UserDaoImpl.getInstance().delete(expected.getUserId());
        User actual = UserDaoImpl.getInstance().getById(expected.getUserId());
        Assert.assertNull(actual);
    }

    @Test
    public void testIsNewUserFalse() throws DaoException {
        User expected = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(expected);
        boolean isNew = UserDaoImpl.getInstance().isNewUser(expected.getLogin());
        Assert.assertFalse(isNew);
    }

    @Test
    public void testIsNewUserTrue() throws DaoException {
        User user1 = EntityBuilder.buildUser(0, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(1, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(user1);
        boolean isNew = UserDaoImpl.getInstance().isNewUser(user2.getLogin());
        Assert.assertTrue(isNew);
    }

    @Test
    public void testCheckUserAuthenticationTrue() throws DaoException {
        User user = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(user);
        boolean isSiqnedUp = UserDaoImpl.getInstance().checkUserAuthentication(user.getLogin(), user.getPassword());
        Assert.assertTrue(isSiqnedUp);
    }

    @Test
    public void testCheckUserAuthenticationFalse() throws DaoException {
        User user1 = EntityBuilder.buildUser(0, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(1, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        UserDaoImpl.getInstance().add(user1);
        boolean isSignedUp = UserDaoImpl.getInstance().checkUserAuthentication(user2.getLogin(), user2.getPassword());
        Assert.assertFalse(isSignedUp);
    }

}
