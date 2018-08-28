package com.demo.reservation.web.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@Getter
@Setter
@ToString
public class Row {

    private LinkedList<Cell> cells;

    public Row() {

        cells = new LinkedList<>();
    }

    public void addLast(Cell cell) {

        cells.addLast(cell);
    }

    public void addFirst(Cell cell) {

        cells.addFirst(cell);
    }
}
