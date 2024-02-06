package dao;

import java.sql.Connection;

import java.sql.SQLException;

public interface ConnectingBuilder {

    Connection getConnection() throws SQLException;

}
