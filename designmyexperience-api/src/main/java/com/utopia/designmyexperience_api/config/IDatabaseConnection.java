package com.utopia.designmyexperience_api.config;

import java.sql.Connection;

/**
 * Represent a database connection. All class that connect to the DB has to implement this interface
 */
public interface IDatabaseConnection {
    public Connection getDbConnection();
}
