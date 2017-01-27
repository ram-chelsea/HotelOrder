package com.pvt.service.impl;

import com.pvt.service.EntityServiceImplTest;

public class UserServiceImplTest extends EntityServiceImplTest {
//
//    @Before
//    @After
//    public void CleanTestDB() throws DaoException, SQLException {
//        Connection connection = PoolManager.getInstance().getConnection();
//        Statement statement = connection.createStatement();
//        statement.executeUpdate(SqlRequest.TRUNCATE_TEST_USERS);
//    }
//
//    @Test
//    public void testGetInstance() {
//        UserServiceImpl firstImpl = UserServiceImpl.getInstance();
//        UserServiceImpl secondImpl = UserServiceImpl.getInstance();
//        Assert.assertEquals(firstImpl.hashCode(), secondImpl.hashCode());
//    }
//
//    @Test
//    public void testAdd() throws ServiceException, SQLException, DaoException {
//        User expected = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
//        UserServiceImpl.getInstance().add(expected);
//        User actual = UserDaoImpl.getInstance().getByLogin(expected.getLogin());
//        Assert.assertTrue(expected.equals(actual));
//    }
//
//    @Test
//    public void testGetAll() throws DaoException, SQLException, ServiceException {
//        List<User> userListExpected = new ArrayList<>();
//        User user1 = EntityBuilder.buildUser(1, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
//        userListExpected.add(user1);
//        User user2 = EntityBuilder.buildUser(2, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
//        userListExpected.add(user2);
//        User user3 = EntityBuilder.buildUser(3, "TEST_LOGIN_3", "TEST_FIRST_NAME_3", "TEST_LAST_NAME_3", "TEST_PASSWORD_3", UserRole.CLIENT);
//        userListExpected.add(user3);
//        for (User onlyClient : userListExpected) {
//            UserDaoImpl.getInstance().add(onlyClient);
//        }
//        List<User> userListActual = UserServiceImpl.getInstance().getAll();
//        Assert.assertTrue(userListExpected.containsAll(userListActual) && userListActual.containsAll(userListExpected));
//    }
//
//    @Test
//    public void testCheckUserAuthenticationTrue() throws DaoException, SQLException, ServiceException {
//        User user = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
//        UserDaoImpl.getInstance().add(user);
//        boolean isSiqnedUp = UserServiceImpl.getInstance().checkUserAuthentication(user.getLogin(), user.getPassword());
//        Assert.assertTrue(isSiqnedUp);
//    }
//
//    @Test
//    public void testCheckUserAuthenticationFalse() throws DaoException, SQLException, ServiceException {
//        User user1 = EntityBuilder.buildUser(0, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
//        User user2 = EntityBuilder.buildUser(1, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
//        UserDaoImpl.getInstance().add(user1);
//        boolean isSignedUp = UserServiceImpl.getInstance().checkUserAuthentication(user2.getLogin(), user2.getPassword());
//        Assert.assertFalse(isSignedUp);
//    }
//
//    @Test
//    public void testGetUserByLogin() throws DaoException, ServiceException, SQLException {
//        User expected = EntityBuilder.buildUser(0, "TEST_LOGIN_1", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
//        User otherUser = EntityBuilder.buildUser(0, "TEST_LOGIN_2", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
//        UserDaoImpl.getInstance().add(expected);
//        UserDaoImpl.getInstance().add(otherUser);
//        User actual = UserServiceImpl.getInstance().getUserByLogin(expected.getLogin());
//        Assert.assertTrue(expected.equals(actual));
//    }
//
//    @Test
//    public void testCheckIsNewUserTrue() throws DaoException, SQLException, ServiceException {
//        User user1 = EntityBuilder.buildUser(0, "TEST_LOGIN_1", "TEST_FIRST_NAME_1", "TEST_LAST_NAME_1", "TEST_PASSWORD_1", UserRole.CLIENT);
//        User preUser = EntityBuilder.buildUser(1, "TEST_LOGIN_2", "TEST_FIRST_NAME_2", "TEST_LAST_NAME_2", "TEST_PASSWORD_2", UserRole.CLIENT);
//        UserDaoImpl.getInstance().add(user1);
//        UserDaoImpl.getInstance().add(preUser);
//        User user2 = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
//        UserDaoImpl.getInstance().delete(user2.getUserId());
//        boolean isNew = UserServiceImpl.getInstance().checkIsNewUser(user2);
//        Assert.assertTrue(isNew);
//    }
//
//    @Test
//    public void testCheckIsNewUserFalse() throws DaoException, SQLException, ServiceException {
//        User preUser = EntityBuilder.buildUser(0, "TEST_LOGIN", "TEST_FIRST_NAME", "TEST_LAST_NAME", "TEST_PASSWORD", UserRole.CLIENT);
//        UserDaoImpl.getInstance().add(preUser);
//        User expected = UserDaoImpl.getInstance().getByLogin(preUser.getLogin());
//        boolean isNew = UserServiceImpl.getInstance().checkIsNewUser(expected);
//        Assert.assertFalse(isNew);
//    }
//

}
