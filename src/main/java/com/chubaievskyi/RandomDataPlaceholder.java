package com.chubaievskyi;

import com.chubaievskyi.exceptions.DatabaseExecutionException;
import com.chubaievskyi.util.InputReader;
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

    public RandomDataPlaceholder(int numberOfLines, AtomicInteger rowCounter){
        this.numberOfLines = numberOfLines;
        this.rowCounter = rowCounter;
    }
    @Override
    public void run() {

        LOGGER.info("Method run() class RandomDataPlaceholder start!");

        try {
            generateProductsInShops();
        } catch (SQLException e) {
            throw new DatabaseExecutionException("Database query execution error", e);
        }
    }

    private void generateProductsInShops() throws SQLException {

        int batchSize = numberOfLines > 10_000 ? 10_000 : 1;
        String insertQuery = "INSERT INTO products_in_shops (shop_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            connection.setAutoCommit(false);
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

                    if (rowCounter.get() % batchSize == 0 || rowCounter.get() >= numberOfLines) {
                        break;
                    }
                }
                preparedStatement.executeBatch();
                connection.commit();
                LOGGER.info("10_000 rows of data added!");
            }
            connection.setAutoCommit(true);
        }
    }
}
