package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DirectConnectingBuilder implements ConnectBuilder {
    @Override
    public Connection getConnection() throws SQLException {
       return DriverManager.getConnection("jdbc:postgresql://localhost:5432/CityRegister", "postgres", "123");
    }
}
