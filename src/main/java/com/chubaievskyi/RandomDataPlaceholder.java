package com.chubaievskyi;

import com.chubaievskyi.exceptions.DatabaseExecutionException;
import com.chubaievskyi.util.InputReader;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class RandomDataPlaceholder implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomDataPlaceholder.class);
    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final int NUMBER_OF_SHOPS = INPUT_READER.getNumberOfShops();
    private static final int NUMBER_OF_PRODUCTS = INPUT_READER.getNumberOfProduct();
    private static final int MAX_QUANTITY = INPUT_READER.getMaxNumberOfProductsSameCategory();
    private static final Faker RANDOM = new Faker();
    private final int numberOfLines;

    public RandomDataPlaceholder(int numberOfLines){
        this.numberOfLines = numberOfLines;
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

        for (int i = 0; i < numberOfLines; i++) {
            int shopId = RANDOM.number().numberBetween(1, NUMBER_OF_SHOPS);
            int productId = RANDOM.number().numberBetween(1, NUMBER_OF_PRODUCTS);
            int quantity = RANDOM.number().numberBetween(1, MAX_QUANTITY);

            String insertQuery = "INSERT INTO products_in_shops (shop_id, product_id, quantity) VALUES (?, ?, ?)";
            try (Connection connection = ConnectionManager.get();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, shopId);
                preparedStatement.setInt(2, productId);
                preparedStatement.setInt(3, quantity);
                preparedStatement.executeUpdate();
            }
        }
    }
}
