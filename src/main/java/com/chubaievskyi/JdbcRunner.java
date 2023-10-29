package com.chubaievskyi;

import com.chubaievskyi.exceptions.DatabaseExecutionException;
import com.chubaievskyi.util.InputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcRunner.class);
    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final String PRODUCT_TYPE = INPUT_READER.getProductType();


    public void run() {
        LOGGER.info("Method run() start!");

        String sql = "CREATE TABLE IF NOT EXISTS test (id_test INT, name_test VARCHAR(50))";
        String sql2 = "DROP TABLE IF EXISTS test";

        try (Connection connection = ConnectionManager.get();
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
