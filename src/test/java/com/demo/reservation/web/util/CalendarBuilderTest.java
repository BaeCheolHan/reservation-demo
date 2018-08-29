package com.demo.reservation.web.util;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.exception.InternalException;
import com.demo.reservation.web.pojo.Calendar;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CalendarBuilderTest {

    @Test
    public void testEmptyCalendar() {

        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Room room = new Room();
            room.setId(Integer.valueOf(i).longValue());
            room.setName("r-" + i);
            rooms.add(room);
        }
        Calendar c = new CalendarBuilder().rooms(rooms).build();
        Assert.assertEquals(0, c.getRows().size());
    }

    @Test(expected = InternalException.class)
    public void test_exceptionByEmptyRooms() {

        List<Reservation> reservations = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Reservation reservation = new Reservation();
            reservation.setId(Integer.valueOf(i).longValue());
            reservation.setRowSequence(i);
            reservations.add(reservation);
        }

        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Reservation reservation = reservations.get(i);
            Room room = new Room();
            room.setId(Integer.valueOf(i).longValue());
            room.setName("r-" + i);
            rooms.add(room);
            reservation.setRoom(room);
        }

        Calendar c = new CalendarBuilder().reservations(reservations).build();
    }
}