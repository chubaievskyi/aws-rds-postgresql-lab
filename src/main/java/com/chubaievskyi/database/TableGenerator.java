package com.chubaievskyi.database;

import com.chubaievskyi.exceptions.DBExecutionException;
import com.chubaievskyi.util.ConnectionManager;
import com.chubaievskyi.util.SQLQueryReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableGenerator.class);


    public void createTables(String path, Connection connection) {

        LOGGER.info("Method run() class TableGenerator start!");

        SQLQueryReader reader = new SQLQueryReader(path);

        while (reader.hasNextQueries()) {
            String query = reader.getNextQuery();
//            Connection connection = ConnectionManager.get();
            try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
                prepareStatement.execute();
            } catch (SQLException e) {
                throw new DBExecutionException("Database query execution error", e);
            }
        }
    }
}
