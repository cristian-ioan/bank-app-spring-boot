package com.banking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CurrencyTypeValidator implements ConstraintValidator<CurrencyType, String> {

    List<String> currencies = Arrays.asList("EUR", "RON");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return currencies.contains(value);

    }
}
