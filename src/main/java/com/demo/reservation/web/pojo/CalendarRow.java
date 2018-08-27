package com.demo.reservation.web.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class CalendarRow {

    private LinkedList<CalendarCell> cells;

    public CalendarRow() {

        cells = new LinkedList<>();
    }

    public void addLast(CalendarCell cell) {

        cells.addLast(cell);
    }

    public void addFirst(CalendarCell cell) {

        cells.addFirst(cell);
    }
}
