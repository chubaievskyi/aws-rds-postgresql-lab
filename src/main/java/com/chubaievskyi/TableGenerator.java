package com.chubaievskyi;

import com.chubaievskyi.exceptions.DatabaseExecutionException;
import com.chubaievskyi.util.SQLQueryReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableGenerator.class);


    public void createTables() {

        LOGGER.info("Method run() class TableGenerator start!");

        SQLQueryReader reader = new SQLQueryReader("tableGenerator.sql");

        while (reader.hasNextQueries()) {
            String query = reader.getNextQuery();

            try (Connection connection = ConnectionManager.get();
                 PreparedStatement prepareStatement = connection.prepareStatement(query)) {
                prepareStatement.execute();
            } catch (SQLException e) {
                throw new DatabaseExecutionException("Database query execution error", e);
            }
        }
    }
}
