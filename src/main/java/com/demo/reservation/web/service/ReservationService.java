package com.demo.reservation.web.service;

import com.demo.reservation.web.dao.ReservationDAO;
import com.demo.reservation.web.entity.ConferenceRoom;
import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationDAO dao;
    private ConferenceRoomService roomService;

    public ReservationService(ReservationDAO dao, ConferenceRoomService roomService) {
        this.dao = dao;
        this.roomService = roomService;
    }

    @PostConstruct
    public void initRooms() {
        // set Room
        for (int i = 0; i < 10; i++) {
            Reservation reservation = new Reservation();
            ConferenceRoom room = roomService.findById(Integer.valueOf(i).longValue());
            reservation.setOwnerName("admin");
            reservation.setRoomId(room.getId());
            Date current = new Date();

            reservation.setDate(current);
        }

        dao.findAll().forEach(System.out::println);
    }

    public List<Reservation> findAll() {
        List<Reservation> results = dao.findAll();
        if (results.isEmpty()) {
            throw new NoContentException("empty!");
        }

        return results;
    }

    public Reservation findById(Long id) {

        return dao.findById(id).orElseThrow(() -> new NotFoundException(id, Reservation.class));
    }
}
