package com.demo.reservation.web.service;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.pojo.Calendar;
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
            Calendar calendar = new Calendar(day, rooms);
            reservations.forEach(calendar::addReservation);
            calendar.removeEmptyRows();
            return calendar;
        } catch (NoContentException e) {
            // generate empty calendar
            try {
                List<Room> rooms = roomService.findAll();
                Calendar calendar = new Calendar(rooms);
                calendar.removeEmptyRows();
                return calendar;
            } catch (NoContentException e1) {
                throw new IllegalStateException("logical Error");
            }

        }
    }

}
