package com.chubaievskyi;

import com.chubaievskyi.exceptions.DatabaseConnectionException;
import com.chubaievskyi.util.InputReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final String URL = INPUT_READER.getUrl();
    private static final String USER_NAME = INPUT_READER.getUsername();
    private static final String PASSWORD = INPUT_READER.getPassword();

    private ConnectionManager() {
    }

    public static Connection open() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database", e);
        }
    }
}
