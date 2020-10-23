package com.angrysamaritan.wimixtest.validators;


import com.angrysamaritan.wimixtest.dto.SignUpReq;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class PasswordMatchesChecker implements ConstraintValidator<PasswordsMatches, SignUpReq> {
    @Override
    public void initialize(PasswordsMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(SignUpReq userDto, ConstraintValidatorContext constraintValidatorContext) {
        return userDto.getPassword().equals(userDto.getPasswordConfirmation());
    }
}
