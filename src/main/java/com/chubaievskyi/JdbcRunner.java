package com.chubaievskyi;

import com.chubaievskyi.exceptions.DatabaseExecutionException;
//import org.postgresql.Driver;
import com.chubaievskyi.util.InputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcRunner.class);
    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final String URL = INPUT_READER.getUrl();
    private static final String USER_NAME = INPUT_READER.getUsername();
    private static final String PASSWORD = INPUT_READER.getPassword();
    private static final int POOL_SIZE = INPUT_READER.getPoolSize();
    private static final String PRODUCT_TYPE = INPUT_READER.getProductType();


    public void run() {
        LOGGER.info("Method run() start!");

//        Class<Driver> driverClass = Driver.class;
        String sql = "CREATE TABLE IF NOT EXISTS test (id_test INT, name_test VARCHAR(50))";
//        String sql = "DROP TABLE IF EXISTS test";
        try (Connection connection = ConnectionManager.open();
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            System.out.println(connection.getSchema());
            boolean executeResult = prepareStatement.execute();
            System.out.println(connection.getTransactionIsolation());
            System.out.println(executeResult);
        } catch (SQLException e) {
            throw new DatabaseExecutionException("Database query execution error", e);
        }
    }
}
