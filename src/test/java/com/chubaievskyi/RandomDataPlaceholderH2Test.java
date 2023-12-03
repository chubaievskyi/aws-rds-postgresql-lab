//package com.chubaievskyi;
//
//import com.chubaievskyi.util.RandomDataPlaceholder;
//import org.h2.tools.Server;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.sql.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RandomDataPlaceholderH2Test {
//
//    private static Server server;
//    private static Connection connection;
//
//    @BeforeAll
//    public static void setUp() throws SQLException {
//        server = Server.createTcpServer().start();
//        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
//        Statement statement = connection.createStatement();
//        statement.execute("CREATE TABLE IF NOT EXISTS test_products_in_shops (test_id SERIAL PRIMARY KEY, " +
//                "test_shop_id INT, test_product_id INT, test_quantity INT NOT NULL);");
//    }
//
//    @Test
//    void testGenerateProductsInShops() throws SQLException {
//        int numberOfLines = 100;
//        AtomicInteger rowCounter = new AtomicInteger(0);
//        RandomDataPlaceholder placeholder = new RandomDataPlaceholder(numberOfLines, rowCounter, connection);
//        String insertQuery = "INSERT INTO test_products_in_shops (test_shop_id, test_product_id, test_quantity) VALUES (?, ?, ?)";
//        placeholder.generateProductsInShops(insertQuery);
//
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM test_products_in_shops");
//        resultSet.next();
//        int rowCount = resultSet.getInt(1);
//        assertEquals(numberOfLines, rowCount);
//
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        server.stop();
//    }
//}
