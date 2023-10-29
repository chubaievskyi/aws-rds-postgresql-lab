package com.chubaievskyi;

import com.chubaievskyi.exceptions.DatabaseConnectionException;
import com.chubaievskyi.util.InputReader;
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
            throw new DatabaseConnectionException("Error connecting to the database", e);
        }
    }

//    private static final String URL = INPUT_READER.getUrl();
//    private static final String USER_NAME = INPUT_READER.getUsername();
//    private static final String PASSWORD = INPUT_READER.getPassword();
//    private static final int POOL_SIZE = INPUT_READER.getPoolSize();
//
//
//    private ConnectionManager() {
//    }
//
//    public static Connection open() {
//        try {
//            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//        } catch (SQLException e) {
//            throw new DatabaseConnectionException("Error connecting to the database", e);
//        }
//    }
}
