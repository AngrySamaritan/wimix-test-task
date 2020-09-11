package com.angrysamaritan.wimixtest.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        int MIN_PASSWORD_LENGTH = 5;
        return (password.length() >= MIN_PASSWORD_LENGTH);
    }
}
