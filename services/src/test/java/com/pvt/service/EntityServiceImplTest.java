package com.pvt.service;

import com.pvt.managers.PoolManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.ResourceBundle;

public abstract class EntityServiceImplTest {
    public static void setDatabaseByPropertiesFile(String propertiesFile) {
        PoolManager poolManager = PoolManager.getInstance();
        BasicDataSource dataSource = poolManager.getDataSource();
        ResourceBundle bundle = ResourceBundle.getBundle(propertiesFile);
        dataSource.setDriverClassName(bundle.getString("database.driver"));
        dataSource.setUrl(bundle.getString("database.url"));
        dataSource.setUsername(bundle.getString("database.user"));
        dataSource.setPassword(bundle.getString("database.password"));

    }

    @BeforeClass
    public static void setTestDatabase() {
        setDatabaseByPropertiesFile("testdatabase");

    }

    @AfterClass
    public static void setWorkDatabase() {
        setDatabaseByPropertiesFile("database");
    }
}