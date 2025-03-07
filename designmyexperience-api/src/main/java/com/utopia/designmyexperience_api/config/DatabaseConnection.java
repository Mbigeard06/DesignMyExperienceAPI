package com.utopia.designmyexperience_api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseConnection implements IDatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/designmyexperience";
    private static final String USER = "mateo";
    private static final String PASSWORD = "MATEOdasen";
    private static final HikariDataSource dataSource;

    // Static block to initialize HikariCP configuration
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10); // Limit the number of connections in the pool
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000); // 30 seconds
        config.setMaxLifetime(1800000); // 30 minutes

        dataSource = new HikariDataSource(config);
    }

    /**
     * Returns a database connection from the HikariCP connection pool.
     * @return Connection object
     */
    @Override
    public Connection getDbConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get database connection", e);
        }
    }
}