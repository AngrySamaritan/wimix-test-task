package com.angrysamaritan.wimixtest.validators;

import com.angrysamaritan.wimixtest.model.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class PasswordMatchesChecker implements ConstraintValidator<PasswordsMatches, UserDto> {
    @Override
    public void initialize(PasswordsMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        return userDto.getPassword().equals(userDto.getPasswordConfirm());
    }
}
