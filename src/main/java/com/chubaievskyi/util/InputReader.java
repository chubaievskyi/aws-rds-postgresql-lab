package com.chubaievskyi.util;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Getter
public class InputReader {

    public static final Logger LOGGER = LoggerFactory.getLogger(InputReader.class);
    private static final Properties PROPERTIES = new PropertiesLoader().loadProperties();
    private static final InputReader INSTANCE = new InputReader();

    private String url;
    private String username;
    private String password;
    private int poolSize;
    private String productType;
    private int numberOfShops;
    private int numberOfProduct;
    private int numberOfOptions;

    private InputReader() {
        readPropertiesValue();
        checkProductType();
    }

    public static InputReader getInstance() {
        return INSTANCE;
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
        url = PROPERTIES.getProperty("db.url");
        username = PROPERTIES.getProperty("db.username");
        password = PROPERTIES.getProperty("db.password");
        poolSize = Integer.parseInt(PROPERTIES.getProperty("db.pool.size"));
        productType = PROPERTIES.getProperty("db.default.product.type");
        numberOfShops = Integer.parseInt(PROPERTIES.getProperty("db.number.of.shops"));
        numberOfProduct = Integer.parseInt(PROPERTIES.getProperty("db.number.of.product"));
        numberOfOptions = Integer.parseInt(PROPERTIES.getProperty("db.number.of.options"));
    }
}
