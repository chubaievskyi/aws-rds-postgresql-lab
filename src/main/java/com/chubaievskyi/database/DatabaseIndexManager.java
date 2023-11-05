package com.chubaievskyi.database;

import com.chubaievskyi.exceptions.DBExecutionException;
import com.chubaievskyi.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseIndexManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseIndexManager.class);

    public void createIndex() {
        LOGGER.info("Creating indexes!");
        String index1 = "CREATE INDEX idx_category_id ON products (category_id);";
        String index2 = "CREATE INDEX idx_product_id ON products_in_shops (product_id);";
        executeQuery(index1);
        executeQuery(index2);
    }

    private void executeQuery(String query) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DBExecutionException("Database query execution error", e);
        }
    }
}
