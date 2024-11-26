package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String DB_URL = "localhost:3306";
    private static final String DB_NAME = "disposition";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        String connectionURL = "jdbc:mysql://" + DB_URL + "/" + DB_NAME;
        return DriverManager.getConnection(connectionURL, DB_USERNAME, DB_PASSWORD);
    }
}
