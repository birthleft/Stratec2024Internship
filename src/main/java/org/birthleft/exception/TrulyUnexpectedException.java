package org.birthleft.exception;

public class TrulyUnexpectedException extends UserFriendlyRuntimeException {
    public TrulyUnexpectedException(String message) {
        super("An unexpected error occurred: " + message);
    }
}
