package com.chubaievskyi.database;

import com.chubaievskyi.exceptions.DBExecutionException;
import com.chubaievskyi.util.ConnectionManager;
import com.chubaievskyi.util.InputReader;
import com.chubaievskyi.util.SQLQueryReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductQueryExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductQueryExecutor.class);
    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final String PRODUCT_TYPE = INPUT_READER.getProductType();

    public void findShopByCategory() {
        LOGGER.info("Method findShopByCategory() class ProductQueryExecutor start!");
        SQLQueryReader reader = new SQLQueryReader("selectQuery.sql");

        while (reader.hasNextQueries()) {
            String query = reader.getNextQuery();

            try (Connection connection = ConnectionManager.get();
                 PreparedStatement prepareStatement = connection.prepareStatement(query)) {
                prepareStatement.setString(1, PRODUCT_TYPE);

                long startTime = System.currentTimeMillis();
                try (ResultSet resultSet = prepareStatement.executeQuery()) {
                    long endTime = System.currentTimeMillis();
                    if (resultSet.next()) {

                        String result = resultSet.getString("shop_name") + ", " +
                                resultSet.getString("city") + ", " +
                                resultSet.getString("street") + ", " +
                                resultSet.getString("number") + ", quantity = " +
                                resultSet.getInt("quantity");
                        long resultTime = endTime - startTime;

                        LOGGER.info("The largest number of products in the {} category is in the store: {}", PRODUCT_TYPE, result);
                        LOGGER.info("Query execution time : {}", resultTime);
                    } else {
                        LOGGER.info("No shop found for '{}' products.", PRODUCT_TYPE);
                    }
                }
            } catch (SQLException e) {
                throw new DBExecutionException("Database query execution error", e);
            }
        }
    }
}
