package com.chubaievskyi;

import com.chubaievskyi.dto.ProductDTO;
import com.chubaievskyi.dto.ShopDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        LOGGER.info("Program start!");

        DBCreator dbCreator = new DBCreator();
        dbCreator.run();


        for (int i = 0; i < 20; i++) {
            ShopDTO shop = new DTOGenerator().generateRandomShop();
            System.out.println(shop.getName() + " + " + shop.getCity() + " + " + shop.getStreet() + " + " + shop.getNumber());
        }

        for (int i = 0; i < 20; i++) {
            ProductDTO product = new DTOGenerator().generateRandomProduct();
            System.out.println(product.getName() + " + " + product.getCategory());
        }



        LOGGER.info("End of program!");
    }
}