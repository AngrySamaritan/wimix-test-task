package com.angrysamaritan.wimixtest.exceptions;

public class ProfileNotFoundException extends NotFoundException {

    public ProfileNotFoundException(long userId) {
        super("Profile of user id = " + userId + " not found");
    }
}
