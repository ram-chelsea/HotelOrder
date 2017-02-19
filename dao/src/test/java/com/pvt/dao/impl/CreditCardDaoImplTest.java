package com.pvt.dao.impl;

import com.pvt.dao.EntityDaoImplTest;

public class CreditCardDaoImplTest extends EntityDaoImplTest {
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
//        Query query = session.createSQLQuery(SqlRequest.TRUNCATE_TEST_CARDS);
//        query.executeUpdate();
//        session.close();
//    }
//
//    @Test
//    public void testGetInstance() {
//        CreditCardDaoImpl firstImpl = CreditCardDaoImpl.getInstance();
//        CreditCardDaoImpl secondImpl = CreditCardDaoImpl.getInstance();
//        Assert.assertEquals(firstImpl, secondImpl);
//    }
//
//    @Test
//    public void testSave() {
//        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
//        CreditCardDaoImpl.getInstance().save(expected);
//        util.getSession().getTransaction().commit();
//        CreditCard actual = (CreditCard) util.getSession().get(CreditCard.class, expected.getCardId());
//        Assert.assertTrue(expected.equals(actual));
//    }
//
////    @Test
////    public void testGetById() {
////        CreditCard falseCard = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
////        CreditCard expected = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
////        util.getSession().save(falseCard);
////        util.getSession().save(expected);
////        util.getSession().getTransaction().commit();
////        CreditCard actual = CreditCardDaoImpl.getInstance().get(expected.getCardId());
////        Assert.assertEquals(expected, actual);
////    }
//
//    @Test
//    public void testByCardNumber() {
//        CreditCard falseCard = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
//        CreditCard expected = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
//        util.getSession().save(falseCard);
//        util.getSession().save(expected);
//        util.getSession().getTransaction().commit();
//        CreditCard actual = CreditCardDaoImpl.getInstance().getByCardNumber(expected.getCardNumber());
//        Assert.assertEquals(expected, actual);
//    }
////
////    @Test
////    public void testTakeMoneyForOrder() {
////        CreditCard expected = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
////        util.getSession().save(expected);
////        int moneyToTake = 50;
////        int oldAmount = expected.getAmount();
////        util.getSession().getTransaction().commit();
////        CreditCardDaoImpl.getInstance().takeMoneyForOrder(expected, moneyToTake);
////        Assert.assertEquals(oldAmount - moneyToTake, (int) expected.getAmount());
////    }
//
//    @Test
//    public void testIsNewCreditCardTrue() {
//        CreditCard card1 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
//        CreditCard card2 = EntityBuilder.buildCreditCard(null, "8765432112345678", true, 500);
//        util.getSession().save(card1);
//        util.getSession().getTransaction().commit();
//        boolean isNew = CreditCardDaoImpl.getInstance().isNewCreditCard(card2.getCardNumber());
//        Assert.assertTrue(isNew);
//    }
//
//    @Test
//    public void testIsNewCreditCardFalse()  {
//        CreditCard card1 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 500);
//        CreditCard card2 = EntityBuilder.buildCreditCard(null, "1234567887654321", true, 1000);
//        util.getSession().save(card1);
//        util.getSession().getTransaction().commit();
//        boolean isNew = CreditCardDaoImpl.getInstance().isNewCreditCard(card2.getCardNumber());
//        Assert.assertFalse(isNew);
//    }

}
