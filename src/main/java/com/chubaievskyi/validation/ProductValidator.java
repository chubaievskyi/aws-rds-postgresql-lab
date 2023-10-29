package com.chubaievskyi.validation;


import com.chubaievskyi.dto.ProductDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductValidator implements ConstraintValidator<ProductValidation, ProductDTO> {

    public static final String REGEX = "^[a-zA-Z]+$";
    public static final Pattern pattern = Pattern.compile(REGEX);

    @Override
    public boolean isValid(ProductDTO productDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (productDTO == null) {
            return false;
        }

        if (constraintValidatorContext == null) {
            return false;
        }

        boolean isValidName = validateName(productDTO.getName());
        boolean isValidCategory = validateCategory(productDTO.getCategory());

        return isValidCategory && isValidName;
    }

    public static boolean validateName(String name) {

        if (name.length() >= 7) {
            for (char c : name.toCharArray()) {
                if (c == 'a') {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validateCategory(String street) {

        Matcher matcher = pattern.matcher(street);
        return matcher.matches();
    }
}
