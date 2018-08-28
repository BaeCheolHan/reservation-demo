package com.demo.reservation.web.service;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.pojo.Calendar;
import com.demo.reservation.web.util.CalendarBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalenderService {

    private ReservationService reservationService;
    private RoomService        roomService;

    public CalenderService(ReservationService reservationService, RoomService roomService) {

        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    public Calendar findDailyCalenderByDay(LocalDate day) {

        try {
            List<Room> rooms = roomService.findAll();
            List<Reservation> reservations = reservationService.findAllByDay(day);

            return new CalendarBuilder().rooms(rooms).reservations(reservations).build();
        } catch (NoContentException e) {
            // generate empty calendar
            try {
                List<Room> rooms = roomService.findAll();

                return new CalendarBuilder().rooms(rooms).build();
            } catch (NoContentException e1) {
                throw new IllegalStateException("logical Error");
            }

        }
    }

}
