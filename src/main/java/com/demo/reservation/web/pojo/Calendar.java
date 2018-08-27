package com.demo.reservation.web.pojo;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.util.TimeUtils;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Getter
@ToString
public class Calendar {

    private List<String>                    header;
    private List<CalendarRow>               rows;
    private Map<String, List<CalendarCell>> map;

    public Calendar(LocalDate day, List<Room> rooms) {

        map = new TreeMap<>();
        rows = new ArrayList<>();
        header = new ArrayList<>();

        header.add("Time Table");
        for (int i = 0; i < 48; i++) {
            rows.add(new CalendarRow());
        }

        for (Room room : rooms) {
            header.add(room.getName());
            LocalDateTime startTime = day.atTime(0, 0, 0, 0);

            for (int minuteSequence = 0; minuteSequence < 48; minuteSequence++) {

                CalendarCell cell = new CalendarCell();
                cell.setSequence(minuteSequence);
                cell.setOwner(" ");
                cell.setStartTime(startTime);
                cell.setEndTime(startTime.plusMinutes(30));
                cell.setOccupied(false);
                cell.setRoomName(room.getName());
                startTime = startTime.plusMinutes(30);

                CalendarRow calendarRow = rows.get(minuteSequence);
                calendarRow.addLast(cell);
            }
            CalendarRow row = new CalendarRow();
            rows.add(row);
        }

        for (int i = 0; i < 48; i++) {
            String timeTable = TimeUtils.sequenceToTimeStr(i);
            CalendarCell cell = new CalendarCell();
            cell.setOwner(timeTable);
            rows.get(i).addFirst(cell);
        }
    }

    public Calendar(List<Room> rooms) {

        this(LocalDate.now(), rooms);
    }

    public void addReservation(Reservation reservation) {

        CalendarRow row = rows.get(reservation.getCellSequence());
        CalendarCell cell = row.getCells()
                .stream()
                .filter(c -> !StringUtils.isEmpty(c.getRoomName()))
                .filter(c -> c.getRoomName().equals(reservation.getRoom().getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("logic error"));

        cell.setOccupied(true);
        cell.setOwner(reservation.getUser().getName());

    }

    public void removeEmptyRows() {

        rows = rows.stream()
                .filter(row -> row.getCells().stream()
                        .filter(cell -> !cell.getOwner().equals("enable booking"))
                        .anyMatch(cell -> !StringUtils.isEmpty(cell.getRoomName()))
                ).collect(Collectors.toList());
    }

}
