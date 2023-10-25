package com.chubaievskyi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class InputReader {

    public static final Logger LOGGER = LoggerFactory.getLogger(InputReader.class);

    private String url;
    private String username;
    private String password;
    private int poolSize;
    private String productType;

    private final Properties properties;

    public InputReader(Properties properties) {
        this.properties = properties;
        readPropertiesValue();
        checkProductType();
    }

    private void checkProductType() {
        String systemProductType = System.getProperty("type");
        if (systemProductType == null) {
            LOGGER.error("The type of product for which to analyze is not specified." +
                    "The default type ({}) will be used.", productType);
        } else {
                productType = systemProductType;
                LOGGER.info("The product type - ({}) - will be used for analysis.", productType);
        }
    }

    private void readPropertiesValue() {
        LOGGER.info("Read the values of properties.");
        url = properties.getProperty("db.url");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
        poolSize = Integer.parseInt(properties.getProperty("db.pool.size"));
        productType = properties.getProperty("db.default.product.type");
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getProductType() {
        return productType;
    }
}
