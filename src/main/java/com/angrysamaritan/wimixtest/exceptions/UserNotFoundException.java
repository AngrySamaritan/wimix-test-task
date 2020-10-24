package com.angrysamaritan.wimixtest.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long userId) {
        super("User id: " + userId + " not found!");
    }
}
