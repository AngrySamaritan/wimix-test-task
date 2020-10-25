package com.angrysamaritan.wimixtest.exceptions;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(long userId) {
        super("User id: " + userId + " not found!");
    }

}
