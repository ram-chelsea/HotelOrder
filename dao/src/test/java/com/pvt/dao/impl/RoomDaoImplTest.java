package com.pvt.dao.impl;

import com.pvt.dao.EntityDaoImplTest;

public class RoomDaoImplTest extends EntityDaoImplTest {
//    private HibernateUtil util = HibernateUtil.getHibernateUtil();
//
//    @Before
//    public void BeforeTest() {
//        util.openSession();
//        util.getSession().beginTransaction();
//    }
//
//    @After
//    public void AfterTestCleanDB() {
//        Session session = util.getSession();
//        Query query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_FALSE);
//        query.executeUpdate();
//        query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_ROOMS);
//        query.executeUpdate();
//        query = session.createSQLQuery(SqlRequest.SET_FOREIGN_KEYS_CHECKS_TRUE);
//        query.executeUpdate();
//        session.close();
//    }
//
//    @Test
//    public void testGetInstance() {
//        RoomDaoImpl firstImpl = RoomDaoImpl.getInstance();
//        RoomDaoImpl secondImpl = RoomDaoImpl.getInstance();
//        Assert.assertEquals(firstImpl, secondImpl);
//    }
//
//    @Test
//    public void testGetAll() {
//        List<Room> roomListExpected = new ArrayList<>();
//        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        roomListExpected.add(room1);
//        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
//        roomListExpected.add(room2);
//        Room room3 = EntityBuilder.buildRoom(null, "203", 3, RoomClass.DELUXE, 150);
//        roomListExpected.add(room3);
//        for (Room room : roomListExpected) {
//            util.getSession().save(room);
//        }
//        util.getSession().getTransaction().commit();
//        List<Room> roomListActual = RoomDaoImpl.getInstance().getAll();
//        Assert.assertTrue(roomListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomListExpected));
//    }
//
//    @Test
//    public void testSave() {
//        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        RoomDaoImpl.getInstance().save(expected);
//        util.getSession().getTransaction().commit();
//        Room actual = (Room) util.getSession().get(Room.class, expected.getRoomId());
//        Assert.assertEquals(expected, actual);
//    }
//
////    @Test
////    public void testGetById() {
////        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
////        Room falseRoom = EntityBuilder.buildRoom(null, "202", 1, RoomClass.SUITE, 5);
////        util.getSession().save(expected);
////        util.getSession().save(falseRoom);
////        util.getSession().getTransaction().commit();
////        Room actual = RoomDaoImpl.getInstance().get(expected.getRoomId());
////        Assert.assertEquals(expected, actual);
////    }
//
//    @Test
//    public void testByRoomNumber() {
//        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        Room falseRoom = EntityBuilder.buildRoom(null, "202", 1, RoomClass.SUITE, 5);
//        util.getSession().save(expected);
//        util.getSession().save(falseRoom);
//        util.getSession().getTransaction().commit();
//        Room actual = RoomDaoImpl.getInstance().getByRoomNumber(expected.getRoomNumber());
//        Assert.assertEquals(expected, actual);
//    }
//
////    @Test
////    public void testDelete() {
////        Room room = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
////        util.getSession().save(room);
////        int id = room.getRoomId();
////        util.getSession().getTransaction().commit();
////        RoomDaoImpl.getInstance().delete(id);
////        Room actual = RoomDaoImpl.getInstance().get(id);
////        Assert.assertNull(actual);
////    }
//
//    @Test
//    public void testIsNewRoomTrue() {
//        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
//        util.getSession().save(room1);
//        util.getSession().getTransaction().commit();
//        boolean isNew = RoomDaoImpl.getInstance().isNewRoom(room2.getRoomNumber());
//        Assert.assertTrue(isNew);
//    }
//
//    @Test
//    public void testIsNewRoomFalse() {
//        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        Room room2 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        util.getSession().save(room1);
//        util.getSession().getTransaction().commit();
//        boolean isNew = RoomDaoImpl.getInstance().isNewRoom(room2.getRoomNumber());
//        Assert.assertFalse(isNew);
//    }
//
////    @Test
////    public void testUpdateNewPrice() {
////        Room expected = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
////        util.getSession().save(expected);
////        int actualPrice = 15;
////        util.getSession().getTransaction().commit();
////        RoomDaoImpl.getInstance().updateRoomPrice(expected.getRoomId(), actualPrice);
////        Assert.assertEquals(actualPrice, (int) expected.getPrice());
////    }
//
//    @Test
//    public void testGetRoominesses() {
//        List<Room> roomList = new ArrayList<>();
//        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        roomList.add(room1);
//        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
//        roomList.add(room2);
//        Room room3 = EntityBuilder.buildRoom(null, "203", 2, RoomClass.DELUXE, 45);
//        roomList.add(room3);
//        Room room4 = EntityBuilder.buildRoom(null, "204", 5, RoomClass.SUITE, 5);
//        roomList.add(room4);
//        Room room5 = EntityBuilder.buildRoom(null, "205", 7, RoomClass.SUITE, 10);
//        roomList.add(room5);
//        List<Integer> expectedRoominessesList = new ArrayList<>();
//        for (Room room : roomList) {
//            util.getSession().save(room);
//            expectedRoominessesList.add(room.getRoominess());
//        }
//        util.getSession().getTransaction().commit();
//        List<Integer> actualRoominessesList = RoomDaoImpl.getInstance().getRoominesses();
//        Assert.assertTrue(expectedRoominessesList.containsAll(actualRoominessesList) && actualRoominessesList.containsAll(expectedRoominessesList));
//    }
//
//    @Test
//    public void testGetPagesOfRooms() {
//        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        util.getSession().save(room1);
//        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
//        util.getSession().save(room2);
//        Room room3 = EntityBuilder.buildRoom(null, "203", 2, RoomClass.DELUXE, 45);
//        util.getSession().save(room3);
//        Room room4 = EntityBuilder.buildRoom(null, "204", 5, RoomClass.SUITE, 5);
//        util.getSession().save(room4);
//        Room room5 = EntityBuilder.buildRoom(null, "205", 7, RoomClass.SUITE, 10);
//        util.getSession().save(room5);
//        util.getSession().getTransaction().commit();
//        List<Room> roomsListExpected = new ArrayList<>();
//        roomsListExpected.add(room3);
//        roomsListExpected.add(room4);
//        List<Room> roomListActual = RoomDaoImpl.getInstance().getPageOfRooms(2,2);
//        Assert.assertTrue(roomsListExpected.containsAll(roomListActual) && roomListActual.containsAll(roomsListExpected));
//    }
//    @Test
//    public void testGetNumberOfPagesWithRooms(){
//        Room room1 = EntityBuilder.buildRoom(null, "201", 1, RoomClass.SUITE, 5);
//        util.getSession().save(room1);
//        Room room2 = EntityBuilder.buildRoom(null, "202", 2, RoomClass.STANDART, 45);
//        util.getSession().save(room2);
//        Room room3 = EntityBuilder.buildRoom(null, "203", 2, RoomClass.DELUXE, 45);
//        util.getSession().save(room3);
//        Room room4 = EntityBuilder.buildRoom(null, "204", 5, RoomClass.SUITE, 5);
//        util.getSession().save(room4);
//        Room room5 = EntityBuilder.buildRoom(null, "205", 7, RoomClass.SUITE, 10);
//        util.getSession().save(room5);
//        util.getSession().getTransaction().commit();
//        List<Room> roomsListExpected = new ArrayList<>();
//        roomsListExpected.add(room3);
//        roomsListExpected.add(room4);
//        int roomPagesNumber = RoomDaoImpl.getInstance().getNumberOfPagesWithRooms(2);
//        Assert.assertEquals(3, roomPagesNumber);
//    }

}
