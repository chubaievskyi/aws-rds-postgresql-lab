package com.chubaievskyi.dto;

import com.chubaievskyi.validation.ShopValidation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ShopValidation
public class ShopDTO {

    private String name;
    private String city;
    private String street;
    private int number;

    public ShopDTO() {
    }

    public ShopDTO(String name, String city, String street, int number) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.number = number;
    }
}