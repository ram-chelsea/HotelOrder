package com.pvtoc.dao.impl;

import com.pvtoc.constants.UserRole;
import com.pvtoc.dao.UserDao;
import com.pvtoc.entities.User;
import com.pvtoc.util.EntityBuilder;
import org.hibernate.Session;
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
@ContextConfiguration("/dao-test-app-context.xml")
@Transactional
@Rollback
public class UserDaoTest {

    @Autowired
    UserDao<User> userDao;

    private Session session;

    @Before
    public void BeforeTest() {
        session = userDao.getSession();
    }

    @Test
    public void testSave() {
        User expected = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        userDao.save(expected);
        session.flush();
        User actual = ( User ) session.get(User.class, expected.getUserId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
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
        List<User> userListActual = userDao.getAllClients();
        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
    }

    @Test
    public void testGet() {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        User falseUser = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(expected);
        session.save(falseUser);
        session.flush();
        User actual = userDao.get(User.class, expected.getUserId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetByLogin() {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        User falseUser = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(expected);
        session.save(falseUser);
        session.flush();
        User actual = userDao.getByLogin(expected.getLogin());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testDelete() {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(expected);
        int id = expected.getUserId();
        userDao.delete(expected);
        session.flush();
        User actual = userDao.get(User.class, id);
        Assert.assertNull(actual);
    }

    @Test
    public void testIsNewUserFalse() {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.ROLE_CLIENT);
        session.save(user1);
        session.flush();
        boolean isNew = userDao.isNewUser(user2.getLogin());
        Assert.assertFalse(isNew);
    }

    @Test
    public void testIsNewUserTrue() {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.ROLE_CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.ROLE_CLIENT);
        session.save(user1);
        session.flush();
        boolean isNew = userDao.isNewUser(user2.getLogin());
        Assert.assertTrue(isNew);
    }

    @Test
    public void testGetPagesOfClients() {
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
        session.flush();
        List<User> userListActual = userDao.getPageOfClients(2, 3);
        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
    }

    @Test
    public void testGetNumberOfPagesWithClients() {
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
        session.flush();
        int numberOfPages = userDao.getNumberOfPagesWithClients(2);
        Assert.assertEquals(3, numberOfPages);
    }

}
