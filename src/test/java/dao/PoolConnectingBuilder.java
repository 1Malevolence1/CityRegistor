package dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.CheckPersonServlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectingBuilder implements ConnectBuilder {
    private static final Logger logger = LoggerFactory.getLogger(PoolConnectingBuilder.class);

    private DataSource dataSource;

    public PoolConnectingBuilder( ) {
        try {
            // рабода с зарегистрированными ресурсами
            Context context = new InitialContext(); // Объект, который работает с зарегестриррованными объектами
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/cityRegister"); // искать ресурсы

        } catch (NamingException e) {
           logger.error("Error", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
