package utils;

import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class Context {
    private final Connection connection;

    public Context() throws SQLException {
        String url = "jdbc:postgresql://localhost:5433/task";
        Properties properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "qwerty");
        connection = DriverManager.getConnection(url, properties);
        connection.setSchema("public");
    }

    public Connection getConnection() {
        return connection;
    }
}
