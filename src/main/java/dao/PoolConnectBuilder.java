package dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoolConnectBuilder implements ConnectBuilder {
    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(PoolConnectBuilder.class);

    public PoolConnectBuilder() {
        try {
            Context ctx = new InitialContext();
            this.dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/cityRegister");
        } catch (NamingException var2) {
            logger.error("", var2);
        }

    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}