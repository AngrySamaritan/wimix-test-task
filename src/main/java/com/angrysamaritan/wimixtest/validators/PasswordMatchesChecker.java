package com.angrysamaritan.wimixtest.validators;

import com.angrysamaritan.wimixtest.DTO.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class PasswordMatchesChecker implements ConstraintValidator<PasswordsMatches, UserDto.PasswordWithConfirmation> {
    @Override
    public void initialize(PasswordsMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserDto.PasswordWithConfirmation userDto, ConstraintValidatorContext constraintValidatorContext) {
        return userDto.getPassword().equals(userDto.getPasswordConfirmation());
    }
}
