package org.birthleft.mapper;

import java.util.HashMap;
import java.util.Map;

public class StringToIntegerMapper {
    private static final Map<String, Integer> map = new HashMap<>();

    static {
        map.put("zero", 0);
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.put("six", 6);
        map.put("seven", 7);
        map.put("eight", 8);
        map.put("nine", 9);
        map.put("ten", 10);
    }

    public static Integer convert(String key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        return map.get(key);
    }
}
