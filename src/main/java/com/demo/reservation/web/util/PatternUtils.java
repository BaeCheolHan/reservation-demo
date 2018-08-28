package com.demo.reservation.web.util;

import java.util.regex.Pattern;

public class PatternUtils {

    private static final Pattern TIME_CELL_PATTERN = Pattern.compile("^[0-9]{2}:[0-9]{2} ~ [0-9]{2}:[0-9]{2}$");

    public static boolean isTimeTableValue(String value) {

        return TIME_CELL_PATTERN.matcher(value).find();
    }
}
