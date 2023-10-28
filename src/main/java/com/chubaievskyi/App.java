package com.chubaievskyi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        LOGGER.info("Program start!");

        JdbcRunner jdbcRunner = new JdbcRunner();
        jdbcRunner.run();

        LOGGER.info("End of program!");
    }
}