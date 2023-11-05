package com.chubaievskyi.util;

import com.chubaievskyi.exceptions.DBExecutionException;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomDataPlaceholder implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomDataPlaceholder.class);
    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final int NUMBER_OF_SHOPS = INPUT_READER.getNumberOfShops();
    private static final int NUMBER_OF_PRODUCTS = INPUT_READER.getNumberOfProduct();
    private static final int MAX_QUANTITY = INPUT_READER.getMaxNumberOfProductsSameCategory();
    private final AtomicInteger rowCounter;
    private static final Faker RANDOM = new Faker();
    private final int numberOfLines;
    private final String insertQuery = "INSERT INTO products_in_shops (shop_id, product_id, quantity) VALUES (?, ?, ?)";
    private final Connection connection;

    public RandomDataPlaceholder(int numberOfLines, AtomicInteger rowCounter, Connection connection){
        this.numberOfLines = numberOfLines;
        this.rowCounter = rowCounter;
        this.connection = connection;
    }
    @Override
    public void run() {

        LOGGER.info("Method run() class RandomDataPlaceholder start!");

        try {
            generateProductsInShops(insertQuery);
        } catch (SQLException e) {
            throw new DBExecutionException("Database query execution error.", e);
        }
    }

    protected void generateProductsInShops(String insertQuery) throws SQLException {

        int batchSize = numberOfLines > 10_000 ? 10_000 : 1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            connection.setAutoCommit(false);
            int count = 0;
            while (rowCounter.get() < numberOfLines) {
                for (int i = 0; i < batchSize; i++) {

                    rowCounter.incrementAndGet();
                    int shopId = RANDOM.number().numberBetween(1, NUMBER_OF_SHOPS);
                    int productId = RANDOM.number().numberBetween(1, NUMBER_OF_PRODUCTS);
                    int quantity = RANDOM.number().numberBetween(1, MAX_QUANTITY);

                    preparedStatement.setInt(1, shopId);
                    preparedStatement.setInt(2, productId);
                    preparedStatement.setInt(3, quantity);
                    preparedStatement.addBatch();
                    count++;

                    if (count % batchSize == 0 || rowCounter.get() >= numberOfLines) {
                        break;
                    }
                }
                int[] updateCounts = preparedStatement.executeBatch();
                preparedStatement.clearBatch();
                connection.commit();
                if (updateCounts.length > 0) {
                    LOGGER.info("{} rows of data added!", updateCounts.length);
                }
            }
            connection.setAutoCommit(true);
        }
    }
}
