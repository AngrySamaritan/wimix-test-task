package com.angrysamaritan.wimixtest.validators;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("security.password.min-length")
    private static int MIN_PASSWORD_LENGTH;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return (password.length() >= MIN_PASSWORD_LENGTH);
    }
}
