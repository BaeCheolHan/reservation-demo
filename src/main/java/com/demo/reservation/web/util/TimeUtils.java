package com.demo.reservation.web.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeUtils {

    public static LocalTime setZeroAfterDay(LocalTime localDateTime) {

        localDateTime = localDateTime.minusHours(localDateTime.getHour());
        localDateTime = localDateTime.minusMinutes(localDateTime.getMinute());
        localDateTime = localDateTime.minusSeconds(localDateTime.getSecond());
        localDateTime = localDateTime.minusNanos(localDateTime.getNano());

        return localDateTime;
    }

    public static List<Integer> getTimeTableSequence(LocalTime startTime, LocalTime endTime) {

        int startTimeToMin = (startTime.getHour() * 60) + (startTime.getMinute());
        int endTimeToSec = (endTime.getHour() * 60) + (endTime.getMinute());

        List<Integer> sequences = new ArrayList<>();
        for (int current = startTimeToMin; current < endTimeToSec; current += 30) {
            sequences.add(current / 30);
        }

        return sequences;
    }

    public static boolean validMinute(LocalTime localTime) {

        return localTime.getMinute() == 0 || localTime.getMinute() == 30;
    }

    public static String sequenceToTimeStr(Integer sequence) {

        LocalTime current = TimeUtils.setZeroAfterDay(LocalTime.now());
        Integer min = sequence * 30;
        LocalTime startTime = current.plusMinutes(min);
        LocalTime endTime = startTime.plusMinutes(30);

        return String.format("%s ~ %s", startTime.format(DateTimeFormatter.ofPattern("HH:mm")), endTime.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public static boolean validRange(LocalTime startTime, LocalTime endTime) {

        return endTime.isAfter(startTime) && !startTime.equals(endTime);
    }
}
