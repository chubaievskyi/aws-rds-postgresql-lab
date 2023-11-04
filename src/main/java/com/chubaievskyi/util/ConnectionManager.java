package com.chubaievskyi.util;

import com.chubaievskyi.exceptions.DBConnectionException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final BasicDataSource DATA_SOURCE = new BasicDataSource();

    static {
        DATA_SOURCE.setUrl(INPUT_READER.getUrl());
        DATA_SOURCE.setUsername(INPUT_READER.getUsername());
        DATA_SOURCE.setPassword(INPUT_READER.getPassword());
        DATA_SOURCE.setInitialSize(INPUT_READER.getPoolSize());
    }

    private ConnectionManager() {
    }

    public static Connection get() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new DBConnectionException("Error connecting to the database", e);
        }
    }
}
