package com.pvt.dao.impl;

import com.pvt.constants.SqlRequest;
import com.pvt.constants.UserRole;
import com.pvt.dao.EntityDaoImplTest;
import com.pvt.entities.User;
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

public class UserDaoImplTest extends EntityDaoImplTest {
    private HibernateUtil util = HibernateUtil.getHibernateUtil();

    @Before
    public void BeforeTest() {
        util.openSession();
        util.getSession().beginTransaction();
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
        UserDaoImpl firstImpl = UserDaoImpl.getInstance();
        UserDaoImpl secondImpl = UserDaoImpl.getInstance();
        Assert.assertEquals(firstImpl, secondImpl);
    }

    @Test
    public void testSave() {
        User expected = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        UserDaoImpl.getInstance().save(expected);
        util.getSession().getTransaction().commit();
        User actual = (User) util.getSession().get(User.class, expected.getUserId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
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
        util.getSession().getTransaction().commit();
        List<User> userListActual = UserDaoImpl.getInstance().getAll();

        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
    }

    @Test
    public void testGetById() {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User falseUser = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(expected);
        util.getSession().save(falseUser);
        util.getSession().getTransaction().commit();
        User actual = UserDaoImpl.getInstance().getById(expected.getUserId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetByLogin() {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User falseUser = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(expected);
        util.getSession().save(falseUser);
        util.getSession().getTransaction().commit();
        User actual = UserDaoImpl.getInstance().getByLogin(expected.getLogin());
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testDelete() {
        User expected = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(expected);
        int id = expected.getUserId();
        util.getSession().getTransaction().commit();
        UserDaoImpl.getInstance().delete(id);
        User actual = UserDaoImpl.getInstance().getById(id);
        Assert.assertNull(actual);
    }

    @Test
    public void testIsNewUserFalse() {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(user1);
        util.getSession().getTransaction().commit();
        boolean isNew = UserDaoImpl.getInstance().isNewUser(user2.getLogin());
        Assert.assertFalse(isNew);
    }

    @Test
    public void testIsNewUserTrue() {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user1);
        util.getSession().getTransaction().commit();
        boolean isNew = UserDaoImpl.getInstance().isNewUser(user2.getLogin());
        Assert.assertTrue(isNew);
    }

    @Test
    public void testCheckUserAuthenticationTrue() {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
        util.getSession().save(user1);
        util.getSession().getTransaction().commit();
        boolean isSiqnedUp = UserDaoImpl.getInstance().checkUserAuthentication(user2.getLogin(), user2.getPassword());
        Assert.assertTrue(isSiqnedUp);
    }

    @Test
    public void testCheckUserAuthenticationFalse() {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user1);
        util.getSession().getTransaction().commit();
        boolean isSignedUp = UserDaoImpl.getInstance().checkUserAuthentication(user2.getLogin(), user2.getPassword());
        Assert.assertFalse(isSignedUp);
    }

    @Test
    public void testGetPagesOfClients() {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        util.getSession().save(user1);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user2);
        User user3 = EntityBuilder.buildUser(null, "TEST_LOGIN_3", "TEST_FIRST_NAME_3", "TEST_LAST_NAME_3", "TEST_PASSWORD_1", UserRole.CLIENT);
        util.getSession().save(user3);
        User user4 = EntityBuilder.buildUser(null, "TEST_LOGIN_4", "TEST_FIRST_NAME_4", "TEST_LAST_NAME_4", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user4);
        User user5 = EntityBuilder.buildUser(null, "TEST_LOGIN_5", "TEST_FIRST_NAME_5", "TEST_LAST_NAME_5", "TEST_PASSWORD_1", UserRole.CLIENT);
        util.getSession().save(user5);
        User user6 = EntityBuilder.buildUser(null, "TEST_LOGIN_6", "TEST_FIRST_NAME_6", "TEST_LAST_NAME_6", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user6);
        User user7 = EntityBuilder.buildUser(null, "TEST_LOGIN_7", "TEST_FIRST_NAME_7", "TEST_LAST_NAME_7", "TEST_PASSWORD_1", UserRole.CLIENT);
        util.getSession().save(user7);
        User user8 = EntityBuilder.buildUser(null, "TEST_LOGIN_8", "TEST_FIRST_NAME_8", "TEST_LAST_NAME_8", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user8);
        util.getSession().getTransaction().commit();
        List<User> userListExpected = new ArrayList<>();
        userListExpected.add(user4);
        userListExpected.add(user5);
        userListExpected.add(user6);
        List<User> userListActual = UserDaoImpl.getInstance().getPageOfClients(2, 3);
        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
    }

    @Test
    public void testGetNumberOfPagesWithClients() {
        User user1 = EntityBuilder.buildUser(null, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
        util.getSession().save(user1);
        User user2 = EntityBuilder.buildUser(null, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user2);
        User user3 = EntityBuilder.buildUser(null, "TEST_LOGIN_3", "TEST_FIRST_NAME_3", "TEST_LAST_NAME_3", "TEST_PASSWORD_1", UserRole.CLIENT);
        util.getSession().save(user3);
        User user4 = EntityBuilder.buildUser(null, "TEST_LOGIN_4", "TEST_FIRST_NAME_4", "TEST_LAST_NAME_4", "TEST_PASSWORD_2", UserRole.CLIENT);
        util.getSession().save(user4);
        User user5 = EntityBuilder.buildUser(null, "TEST_LOGIN_5", "TEST_FIRST_NAME_5", "TEST_LAST_NAME_5", "TEST_PASSWORD_1", UserRole.CLIENT);
        util.getSession().save(user5);
        util.getSession().getTransaction().commit();
        int numberOfPages = UserDaoImpl.getInstance().getNumberOfPagesWithClients(2);
        Assert.assertEquals(3, numberOfPages);
    }

}
