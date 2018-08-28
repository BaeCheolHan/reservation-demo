package com.demo.reservation.web.util;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.exception.InternalException;
import com.demo.reservation.web.pojo.Calendar;
import com.demo.reservation.web.pojo.Cell;
import com.demo.reservation.web.pojo.Row;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalendarBuilder {

    private static final int               MAX_ROW_SEQUENCE = 47;
    private              Calendar          calendar;
    private              List<Room>        rooms;
    private              List<Reservation> reservations;

    public CalendarBuilder() {

        calendar = new CalendarImpl();
    }

    public CalendarBuilder rooms(List<Room> rooms) {

        this.rooms = rooms;
        return this;
    }

    public CalendarBuilder reservations(List<Reservation> reservations) {

        this.reservations = reservations;
        return this;
    }

    public Calendar build() throws InternalException{

        initHeader();
        initRows();
        initCells();
        Optional.ofNullable(reservations).ifPresent(e -> addReservations());
        removeEmptyRows();

        return this.calendar;
    }

    private void initHeader() {

        List<String> header = calendar.getHeader();
        header.add("Time Table");

        Optional.ofNullable(rooms).ifPresent(rooms -> {
            rooms.forEach(room -> header.add(room.getName()));
        });

    }

    private void initRows() {

        // generate empty rows
        List<Row> rows = calendar.getRows();
        for (int i = 0; i <= MAX_ROW_SEQUENCE; i++) {
            rows.add(new Row());
        }
    }

    private void initCells() {

        List<Row> rows = calendar.getRows();
        // init time table value
        for (int rowSequence = 0; rowSequence <= MAX_ROW_SEQUENCE; rowSequence++) {
            String timeTable = TimeUtils.sequenceToTimeStr(rowSequence);
            Cell cell = new Cell();
            cell.setValue(timeTable);

            rows.get(rowSequence).addFirst(cell);
        }

        // fulfill by empty values
        Optional.ofNullable(rooms).ifPresent(rooms ->
                rooms.forEach(room -> {
                    for (int rowSequence = 0; rowSequence <= MAX_ROW_SEQUENCE; rowSequence++) {
                        Cell cell = new Cell();
                        cell.setRowSequence(rowSequence);
                        cell.setValue(" ");
                        cell.setRoomId(room.getId());

                        Row row = rows.get(rowSequence);
                        row.addLast(cell);
                    }
                })
        );

    }

    private void addReservations() throws InternalException {

        Optional.ofNullable(reservations).ifPresent(reservations -> {
            reservations.forEach(reservation -> {
                List<Row> rows = calendar.getRows();
                //find row
                Row row = rows.get(reservation.getCellSequence());

                //find cell
                Cell cell = row.getCells()
                        .stream()
                        .filter(c -> !Objects.isNull(c.getRoomId()))
                        .filter(c -> c.getRoomId().equals(reservation.getRoom().getId()))
                        .findFirst()
                        .orElseThrow(() -> new InternalException(String.format("could not found matched cell for [ %s / %s ]", reservation.getRoom().getName(),
                                TimeUtils.sequenceToTimeStr(reservation.getCellSequence()))
                                )
                        );

                cell.setValue(reservation.getUser().getName());
            });
        });

    }

    private void removeEmptyRows() {

        List<Row> rows = calendar.getRows();
        List<Row> emptyRows = rows.stream().filter(this::isEmptyRow).collect(Collectors.toList());
        rows.removeAll(emptyRows);
    }

    private boolean isEmptyRow(Row row) {

        List<Cell> cells = row.getCells();
        //  only have a time table value.
        boolean isEmpty = cells.stream().filter(c -> !c.getValue().equals(" ")).anyMatch(c -> !PatternUtils.isTimeTableValue(c.getValue()));

        return isEmpty;
    }

    @Getter
    @Setter
    @ToString
    private class CalendarImpl implements Calendar {

        private List<String> header;
        private List<Row>    rows;

        private CalendarImpl() {

            rows = new ArrayList<>();
            header = new ArrayList<>();
        }
    }
}
