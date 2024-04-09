package org.birthleft.exception;

public class CapacityValidatorException extends UserFriendlyRuntimeException {
    public CapacityValidatorException(Integer line, String message) {
        super("Validating capacity failed! At line " + line + ": " + message);
    }
}
