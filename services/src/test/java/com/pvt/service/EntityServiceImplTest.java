package com.pvt.service;

import com.pvt.entities.CreditCard;
import com.pvt.entities.Order;
import com.pvt.entities.Room;
import com.pvt.entities.User;
import com.pvt.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Properties;

public abstract class EntityServiceImplTest {
    public static void setDatabaseByPropertiesFile(String propertiesFile) {
        HibernateUtil util = HibernateUtil.getHibernateUtil();
        try {
            java.util.Properties properties = new Properties();
            properties.load(HibernateUtil.class.getClassLoader().getSystemClassLoader().getResourceAsStream(propertiesFile));
            Configuration configuration = new Configuration()
                    .addProperties(properties)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(CreditCard.class)
                    .addAnnotatedClass(Room.class)
                    .addAnnotatedClass(Order.class);
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            util.setSessionFactory(sessionFactory);
        } catch (Throwable e) {
            Logger.getLogger(HibernateUtil.class)
                    .error("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    @BeforeClass
    public static void setTestDatabase() {
        setDatabaseByPropertiesFile("hibernate.testdatabase.properties");
    }

    @AfterClass
    public static void setWorkDatabase() {
        setDatabaseByPropertiesFile("hibernate.database.properties");
    }
}