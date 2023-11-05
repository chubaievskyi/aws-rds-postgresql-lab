package com.chubaievskyi;

import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class H2DatabaseTest {

    private static Server server;

    @BeforeAll
    public static void setUp() throws SQLException {

        server = Server.createTcpServer().start();
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(255))");
        statement.execute("INSERT INTO users VALUES (1, 'John')");
    }

    @Test
    void testH2Database() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement statement = connection.createStatement();
        assertEquals(1, statement.executeUpdate("UPDATE users SET name = 'Jane' WHERE id = 1"));

        ResultSet resultSet = statement.executeQuery("SELECT name FROM users WHERE id = 1");
        resultSet.next();
        assertEquals("Jane", resultSet.getString("name"));
    }

    @AfterAll
    public static void stopDb() {
        server.stop();
    }
}
