package com.chubaievskyi;

import com.chubaievskyi.dto.ShopDTO;
import com.chubaievskyi.exceptions.DatabaseExecutionException;
import com.chubaievskyi.util.InputReader;
import com.chubaievskyi.util.SQLQueryReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ValueGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueGenerator.class);
    private static final InputReader INPUT_READER = InputReader.getInstance();
    private static final int NUMBER_OF_SHOPS = INPUT_READER.getNumberOfShops();
    private static final int NUMBER_OF_PRODUCTS = INPUT_READER.getNumberOfProduct();
    private static final int NUMBER_OF_OPTIONS = INPUT_READER.getNumberOfOptions();
    DTOGenerator dtoGenerator = new DTOGenerator();

    public void run() {

        LOGGER.info("Method run() class ValueGenerator start!");

        try {
            generateShopValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        generateProductValue();
    }

    private void generateShopValue() throws SQLException {
        int shopCounter = 0;

        Connection connection = ConnectionManager.get();
        connection.setAutoCommit(false);

        try {
            while (shopCounter < NUMBER_OF_SHOPS) {
                ShopDTO shop = dtoGenerator.generateRandomShop();

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

        } catch (SQLException e) {
            connection.rollback();
            throw new DatabaseExecutionException("Database query execution error", e);
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
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
            throw new DatabaseExecutionException("Database query execution error", e);
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
            throw new DatabaseExecutionException("Database query execution error", e);
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
            throw new DatabaseExecutionException("Database query execution error", e);
        }

        return id;
    }


    private void generateProductValue() {

    }
}
