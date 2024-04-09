package org.birthleft.exception;

public class FileMissingSegmentException extends UserFriendlyRuntimeException{
    public FileMissingSegmentException(String[] missingAttributes) {
        super("The file is missing attributes! Missing attributes: " + String.join(", ", missingAttributes) + "!");
    }
}
