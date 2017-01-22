package com.pvt.managers;

import com.pvt.exceptions.DaoException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Class being used for organizing multithread connection to database
 */
public class PoolManager {
    private static Logger logger = Logger.getLogger(PoolManager.class);
    /**
     * Singleton object variable of <tt>PoolManager</tt> class
     */
    private static PoolManager instance;
    /**
     * Gives ThreadLocal variable of <tt>Connection</tt> for each thread
     */
    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<>();
    /**
     * <tt>DataSource</tt> object for organizing Connection Pool for database
     */
    private BasicDataSource dataSource;

    /**
     * Creates variable <tt>PoolManager</tt> with pre-settings for database connection
     */
    private PoolManager() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(bundle.getString("database.driver"));
        dataSource.setUrl(bundle.getString("database.url"));
        dataSource.setUsername(bundle.getString("database.user"));
        dataSource.setPassword(bundle.getString("database.password"));
    }
    /**
     * Describes synchronized method of getting <tt>PoolManager</tt> singleton object
     *
     * @return <tt>PoolManager</tt> singleton object
     */
    public static synchronized PoolManager getInstance() {
        if (instance == null) {
            instance = new PoolManager();
        }
        return instance;
    }

    /**
     *  Connects to database with dataSource attributes settings
     * @return object of <tt>Connection</tt> to database
     * @throws SQLException
     */
    private Connection connect() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Returns <tt>Connection</tt> object property of current connection thread or set it if it didn't exist
     * @return <tt>Connection</tt> object property of current connection thread or set it if it didn't exist
     * @throws DaoException
     */
    public Connection getConnection() throws DaoException {
        try {
            if (threadConnection.get() == null) {
                Connection connection = connect();
                threadConnection.set(connection);
            }
        } catch (SQLException e) {
            String message = "Unable to get connection";
            throw new DaoException(message, e);
        }
        return threadConnection.get();
    }

    /**
     * Closes <tt>Connection</tt> object <i>connection</i>
     * @param connection needs to be closed
     */
    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                threadConnection.remove();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
}