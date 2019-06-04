package ir.ac.ut.dataAccess;

import org.apache.commons.dbcp.*;
import java.sql.*;


public class ConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:mysql://185.166.107.169:31483/joboonja?useUnicode=yes&characterEncoding=UTF-8";

    static {
    	try {
    	    Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        ds.setUrl(dbURL);
    	ds.setUsername("root");
    	ds.setPassword("root");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        setEncoding();
    }

    public static String getAlterEncodingString(){
        return "ALTER DATABASE joboonja CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;";
    }

    public static void setEncoding(){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(getAlterEncodingString());
            connection.close();
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() {
        while (true) {
            try {
                return ds.getConnection();
            } catch (SQLException ignored){
                System.out.println("Retry to connect....!");
            }
        }
    }
}