package com.chubaievskyi.validation;


import com.chubaievskyi.dto.ShopDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopValidator implements ConstraintValidator<ShopValidation, ShopDTO> {

    public static final String REGEX = "^[a-zA-Z]+$";
    public static final Pattern pattern = Pattern.compile(REGEX);

    @Override
    public boolean isValid(ShopDTO shopDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (shopDTO == null) {
            return false;
        }

        if (constraintValidatorContext == null) {
            return false;
        }

        boolean isValidCity = validateCity(shopDTO.getCity());
        boolean isValidStreet = validateStreet(shopDTO.getStreet());
        boolean isValidNumber = validateNumber(shopDTO.getNumber());

        return isValidStreet && isValidCity && isValidNumber;
    }

    public static boolean validateCity(String name) {

        if (name.length() >= 5) {
            for (char c : name.toCharArray()) {
                if (c == 'a') {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validateStreet(String street) {

        Matcher matcher = pattern.matcher(street);
        return matcher.matches();
    }

    public static boolean validateNumber(int count) {
        return count <= 100;
    }
}
