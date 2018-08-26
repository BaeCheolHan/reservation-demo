package com.demo.reservation.web.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CalenderService {

    public List<Date> findMonthlyMapByDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        List<Date> dates = new ArrayList<>();
        for (int i = 0; i < calendar.getMaximum(Calendar.DAY_OF_MONTH); i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            dates.add(calendar.getTime());
        }

        System.out.println(String.format("calendar : [%d]", dates.size()));
        return dates;
    }
}
