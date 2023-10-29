package com.chubaievskyi;

import com.chubaievskyi.dto.ProductDTO;
import com.chubaievskyi.dto.ShopDTO;
import com.github.javafaker.Faker;

public class DTOGenerator {

    private static final String NAME = "Shop #";
    private static final int MIN_COUNT = 0;
    private static final int MAX_COUNT = 1000;

    private final Faker faker;

    public DTOGenerator() {
        this.faker = new Faker();
    }

    public ShopDTO generateRandomShop() {

        String name = NAME + faker.number().numberBetween(MIN_COUNT, MAX_COUNT);
        String city = faker.address().cityName();
        String street = faker.address().streetName();
        int number = faker.number().numberBetween(MIN_COUNT, MAX_COUNT);

        return new ShopDTO(name, city, street, number);
    }

    public ProductDTO generateRandomProduct() {

        String name = faker.commerce().productName();
        String category = faker.commerce().department();

        return new ProductDTO(name, category);
    }
}
