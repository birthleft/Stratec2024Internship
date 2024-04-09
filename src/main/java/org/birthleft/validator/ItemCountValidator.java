package org.birthleft.validator;

import org.birthleft.exception.FileInvalidSegmentException;

public class ItemCountValidator {
    public static Integer compute(String itemCount, Integer line) {
        String[] itemCountChunks = itemCount.split(" ");
        if (itemCountChunks.length != 2) {
            throw new FileInvalidSegmentException(line, "Can't be split into two parts!");
        }
        if (!itemCountChunks[1].equals("item") && !itemCountChunks[1].equals("items")) {
            throw new FileInvalidSegmentException(line, "If there is a number of items, it must be followed by either 'item' or 'items'!");
        }
        Integer result = Integer.parseInt(itemCountChunks[0]);
        if (result < 1) {
            throw new FileInvalidSegmentException(line, "Number of items must be greater than 0!");
        }
        return result;
    }
}
