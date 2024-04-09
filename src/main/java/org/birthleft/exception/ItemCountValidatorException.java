package org.birthleft.exception;

public class ItemCountValidatorException extends UserFriendlyRuntimeException {
    public ItemCountValidatorException(Integer line, String message) {
        super("Validating item count failed! At line " + line + ": " + message);
    }
}
