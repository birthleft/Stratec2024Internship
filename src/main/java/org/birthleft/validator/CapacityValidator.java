package org.birthleft.validator;

import org.birthleft.exception.CapacityValidatorException;
import org.birthleft.exception.FileInvalidSegmentException;
import org.birthleft.mapper.StringToIntegerMapper;

public class CapacityValidator {
    public static Integer compute(String capacity, Integer line) {
        String[] capacityChunks = capacity.split(" ", 2);
        if (capacityChunks.length != 2) {
            throw new CapacityValidatorException(line, "Can't be split into two parts!");
        }
        if (capacityChunks[0].equals("no") && !capacityChunks[1].equals("limit")) {
            throw new FileInvalidSegmentException(line, "The only way to put 'no' is with 'limit'!");
        }
        if (!(capacityChunks[1].equals("part at a time") || capacityChunks[1].equals("parts at a time"))) {
            throw new FileInvalidSegmentException(line, "If there is a limit, it must be followed by 'part at a time' or 'parts at a time'!");
        }
        Integer result = StringToIntegerMapper.convert(capacityChunks[0]);
        if (result == -1) {
            throw new FileInvalidSegmentException(line, "Number cannot be mapped!");
        }
        return result;
    }
}
