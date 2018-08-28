package com.demo.reservation.web.util;

import org.junit.Assert;
import org.junit.Test;

public class PatternUtilsTest {

    @Test
    public void test_isTimeTableValue() {

        String testTrue = "00:00 ~ 00:30";
        String testFalse = "100:00 ~ 00:30";
        String testFalse2 = "00:00 ~ 00:300";
        String testFalse3 = "PatternUtils!";
        Assert.assertTrue(PatternUtils.isTimeTableValue(testTrue));
        Assert.assertTrue(!PatternUtils.isTimeTableValue(testFalse));
        Assert.assertTrue(!PatternUtils.isTimeTableValue(testFalse2));
        Assert.assertTrue(!PatternUtils.isTimeTableValue(testFalse3));
    }
}