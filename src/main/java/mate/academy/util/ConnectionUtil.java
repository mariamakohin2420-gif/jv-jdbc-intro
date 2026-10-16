package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        PROPERTIES.put("user", "root");
        PROPERTIES.put("password", "your_password");
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/init_db?serverTimezone=UTC";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, PROPERTIES);
    }
}
