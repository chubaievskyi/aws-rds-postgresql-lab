package com.chubaievskyi.database;

import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableGeneratorTest {

    private static Server server;
    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        server = Server.createTcpServer().start();
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    }

    @Test
    void testCreateTables() throws SQLException {

        TableGenerator tableGenerator = new TableGenerator();
        tableGenerator.createTables("testTableGenerator.sql", connection);

        ArrayList<String> expectedTables = new ArrayList<>();
        expectedTables.add("test_products_in_shops");
        expectedTables.add("test_shops");
        expectedTables.add("test_city");
        expectedTables.add("test_street");
        expectedTables.add("test_number");
        expectedTables.add("test_products");
        expectedTables.add("test_category");

        ResultSet tables = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
        int tableCount = 0;

        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME").toLowerCase();
            if (expectedTables.contains(tableName)) {
                tableCount++;
            }
        }
        assertEquals(expectedTables.size(), tableCount);
    }

    @AfterAll
    public static void tearDown() {
        server.stop();
    }
}

