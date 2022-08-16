package utils;

import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class Context {
    private final Connection connection;

    public Context() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5433/task";
        Properties properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "qwerty");
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, properties);
        connection.setSchema("public");
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
