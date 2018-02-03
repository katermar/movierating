package com.katermar.movierating.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by katermar on 1/4/2018.
 */
class DatabaseManager {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);

    private static final String DB = "project";
    private static final String URL = "db.url";
    private static final String USER = "db.user";
    private static final String PASSWORD = "db.password";
    private static DatabaseManager instance;

    private DatabaseManager() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    Connection getConnection() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DB);
        String url = resourceBundle.getString(URL);
        String user = resourceBundle.getString(USER);
        String password = resourceBundle.getString(PASSWORD);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
        return connection;
    }
}
