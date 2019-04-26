package ir.ac.ut.dataAccess;

import org.apache.commons.dbcp.*;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:sqlite:JobOonJa.db";

    static {
        ds.setUrl(dbURL);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}