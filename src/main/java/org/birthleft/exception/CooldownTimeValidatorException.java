package org.birthleft.exception;

public class CooldownTimeValidatorException extends UserFriendlyRuntimeException {
    public CooldownTimeValidatorException(Integer line, String message) {
        super("Validating cooldown time failed! At line " + line + ": " + message);
    }
}
