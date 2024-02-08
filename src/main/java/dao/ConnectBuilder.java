package dao;



import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectBuilder {
    Connection getConnection() throws SQLException;
}
