package com.chubaievskyi.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertyLoaderTest {

    private PropertyLoader propertyLoader;

    @BeforeEach
    public void setup() {
        propertyLoader = new PropertyLoader();
    }

    @Test
    void loadPropertiesWhenPropertiesFileExists() {

        propertyLoader.loadProperties();
        Properties properties = propertyLoader.loadProperties();

        assertEquals("jdbc:postgresql://localhost:5432/company_repository", properties.getProperty("db.url"));
        assertEquals("postgres", properties.getProperty("db.username"));
        assertEquals("postgres", properties.getProperty("db.password"));
        assertEquals("5", properties.getProperty("db.pool.size"));
    }
}