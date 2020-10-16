package com.angrysamaritan.wimixtest.exceptions;

public class NoEmailSetException extends RuntimeException {
    public NoEmailSetException(long id) {
        super(String.format("No email set for user id = %d, so stats report can't be sent", id));
    }
}
