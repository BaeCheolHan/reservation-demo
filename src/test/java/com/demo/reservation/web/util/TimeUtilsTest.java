package com.demo.reservation.web.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeUtilsTest {

    @Test
    public void test_setZeroAfterDay() {

        LocalTime current = TimeUtils.setZeroAfterDay(LocalTime.now());
        Assert.assertEquals(0, current.getHour());
        Assert.assertEquals(0, current.getMinute());
        Assert.assertEquals(0, current.getSecond());
    }

    @Test
    public void test_getCellSequences() {

        LocalTime start = TimeUtils.setZeroAfterDay(LocalTime.now());
        LocalTime end = start.plusMinutes(60);
        // 00:00 ~ 01:00
        // expected : 0 [00:00 ~ 00:30], 1 [00:30 ~ 01:00]
        // rowSequence = startTime(min total) / 30
        List<Integer> sequences = TimeUtils.getTimeTableSequence(start, end);
        Assert.assertEquals(2, sequences.size());
        for (int i = 0; i < sequences.size(); i++) {
            Assert.assertEquals(Integer.valueOf(i), sequences.get(i));
        }


        // 13:00 ~ 15:30
        // rowSequence = startTime(min total) / 30
        // expected : 26[13:00 ~ 13:30], 27[13:30 ~ 14:00], 28[14:00 ~ 14:30], 29[14:30 ~ 15:00], 30[15:00 ~ 15:30]
        start = TimeUtils.setZeroAfterDay(LocalTime.now());
        start = start.plusHours(13);
        end = start.plusMinutes(150);
        sequences = TimeUtils.getTimeTableSequence(start, end);
        for (int i = 26; i < sequences.size(); i++) {
            Assert.assertEquals(Integer.valueOf(i), sequences.get(i - 26));
        }
    }

    @Test
    public void test() {
        String dateStr = "2018-08-24";

        System.out.println(LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE));

        String timeStr = "07:00";

        System.out.println(LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm")));

        for (int i = 0; i < 48; i++) {
            System.out.println(String.format("[%d] - %s", i, TimeUtils.sequenceToTimeStr(i)));
        }
    }
}