package org.example.chef__fizzy.exception;

public class UserUpdateFailedException extends RuntimeException {

    public UserUpdateFailedException(String message) {
        super(message);
    }
}
