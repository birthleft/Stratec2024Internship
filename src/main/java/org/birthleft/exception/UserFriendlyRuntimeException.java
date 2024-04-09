package org.birthleft.exception;

public class UserFriendlyRuntimeException extends RuntimeException{
    public UserFriendlyRuntimeException(String message) {
        super(message);
    }
}
