package com.chubaievskyi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        LOGGER.info("Program start!");

        new DBCreator().run();

        LOGGER.info("End of program!");

        // todo select запит!!!!!!!!!!!!!!!
        // todo виправити sql та properties файли на інстансі!!!!!!!!!!!!!!!
        // todo створити індекси!!!!!!!!!!!!!!!
        // todo згрупувати по пекеджах!!!!!!!!!!!!!!!
        // todo тести!!!!!!!!!!!!!!!
        // todo SONAR!!!!!!!!!!!!!!!
    }
}