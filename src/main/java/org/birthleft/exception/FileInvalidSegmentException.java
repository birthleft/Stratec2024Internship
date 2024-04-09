package org.birthleft.exception;

public class FileInvalidSegmentException extends UserFriendlyRuntimeException {
    public FileInvalidSegmentException(Integer line, String message) {
        super("File has an invalid segment! At line " + line + ": " + message);
    }
}
