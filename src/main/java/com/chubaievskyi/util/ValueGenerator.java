package com.chubaievskyi.util;

import com.chubaievskyi.dto.DTOGenerator;
import com.chubaievskyi.dto.ProductDTO;
import com.chubaievskyi.dto.ShopDTO;
import com.chubaievskyi.exceptions.DBExecutionException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Set;

public class ValueGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueGenerator.class);
    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final int NUMBER_OF_SHOPS = INPUT_READER.getNumberOfShops();
    private static final int NUMBER_OF_PRODUCTS = INPUT_READER.getNumberOfProduct();
    private static final DTOGenerator dtoGenerator = new DTOGenerator();
    private final Validator validator = initializeValidator();

    public void generateValue() {

        LOGGER.info("Method generateValue() class ValueGenerator start!");

        try {
            generateShopValue();
            generateProductValue();
        } catch (SQLException e) {
            throw new DBExecutionException("Error database query execution (method ValueGenerator).", e);
        }
    }

    private void generateShopValue() throws SQLException {
        int shopCounter = 0;

        Connection connection = ConnectionManager.get();
        connection.setAutoCommit(false);

        try {
            while (shopCounter < NUMBER_OF_SHOPS) {
                ShopDTO shop = dtoGenerator.generateRandomShop();
                if (checkDTOBeforeTransfer(shop, validator)) {
                    int cityId = insertCity(connection, shop.getCity());
                    int streetId = insertStreet(connection, shop.getStreet());
                    int numberId = insertNumber(connection, shop.getNumber());

                    String checkQuery = "SELECT id FROM shops WHERE name = ?";
                    int shopId = checkStatement(connection, checkQuery, shop.getName());

                    if (shopId == 0) {
                        insertShop(connection, shop.getName(), cityId, streetId, numberId);
                        connection.commit();
                        shopCounter++;
                    } else {
                        connection.rollback();
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new DBExecutionException("Database query execution error (method generateShopValue).", e);
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private boolean checkDTOBeforeTransfer(Object obj, Validator validator) {
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        return violations.isEmpty();
    }

    private void insertShop(Connection connection, String shopName, int cityId, int streetId, int numberId) {
        String sql = "INSERT INTO shops (name, city_id, street_id, number_id) VALUES (?, ?, ?, ?);";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, shopName);
            prepareStatement.setInt(2, cityId);
            prepareStatement.setInt(3, streetId);
            prepareStatement.setInt(4, numberId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBExecutionException("Database query execution error (method insertShop).", e);
        }
    }

    private int insertCity(Connection connection, String cityName) {
        String checkQuery = "SELECT id FROM city WHERE name = ?";
        String insertQuery = "INSERT INTO city (name) VALUES (?)";
        int cityId = checkStatement(connection, checkQuery, cityName);
        return cityId != 0 ? cityId : insertStatement(connection, insertQuery, cityName);
    }

    private int insertStreet(Connection connection, String streetName) {
        String checkQuery = "SELECT id FROM street WHERE name = ?";
        String insertQuery = "INSERT INTO street (name) VALUES (?)";
        int streetId = checkStatement(connection, checkQuery, streetName);
        return streetId != 0 ? streetId : insertStatement(connection, insertQuery, streetName);
    }

    private int insertNumber(Connection connection, String numberName) {
        String checkQuery = "SELECT id FROM number WHERE name = ?";
        String insertQuery = "INSERT INTO number (name) VALUES (?)";
        int numberId = checkStatement(connection, checkQuery, numberName);
        return numberId != 0 ? numberId : insertStatement(connection, insertQuery, numberName);
    }

    private int checkStatement(Connection connection, String sql, String name) {
        int id = 0;
        try (PreparedStatement checkStatement = connection.prepareStatement(sql)) {
            checkStatement.setString(1, name);
            ResultSet checkResultSet = checkStatement.executeQuery();
            if (checkResultSet.next()) {
                id = checkResultSet.getInt("id");
            } else {
                return id;
            }
        } catch (SQLException e) {
            throw new DBExecutionException("Database query execution error (method checkStatement).", e);
        }
        return id;
    }

    private int insertStatement(Connection connection, String sql, String name) {
        int id = 0;
        try (PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, name);
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new DBExecutionException("Database query execution error (method insertStatement).", e);
        }
        return id;
    }

    private void generateProductValue() throws SQLException {
        int productCounter = 0;

        Connection connection = ConnectionManager.get();
        connection.setAutoCommit(false);

        try {
            while (productCounter < NUMBER_OF_PRODUCTS) {
                ProductDTO product = dtoGenerator.generateRandomProduct();
                if (checkDTOBeforeTransfer(product, validator)) {
                    int categoryId = insertCategory(connection, product.getCategory());

//                    String checkQuery = "SELECT id FROM products WHERE name = ?";
//                    int productsId = checkStatement(connection, checkQuery, product.getName());

//                    if (productsId == 0) {
                        insertProduct(connection, product.getName(), categoryId);
                        connection.commit();
                        productCounter++;
//                    } else {
//                        connection.rollback();
//                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new DBExecutionException("Database query execution error (method generateProductValue).", e);
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private int insertCategory(Connection connection, String categoryName) {
        String checkQuery = "SELECT id FROM category WHERE name = ?";
        String insertQuery = "INSERT INTO category (name) VALUES (?)";
        int categoryId = checkStatement(connection, checkQuery, categoryName);
        return categoryId != 0 ? categoryId : insertStatement(connection, insertQuery, categoryName);
    }

    private void insertProduct(Connection connection, String productsName, int categoryId) {
        String sql = "INSERT INTO products (name, category_id) VALUES (?, ?);";
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, productsName);
            prepareStatement.setInt(2, categoryId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBExecutionException("Database query execution error (method insertProduct).", e);
        }
    }

    private Validator initializeValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
