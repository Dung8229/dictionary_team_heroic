package General;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/anhvietdic";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Ngocbao@1";
    public Connection dbLink;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dbLink;
    }
}
