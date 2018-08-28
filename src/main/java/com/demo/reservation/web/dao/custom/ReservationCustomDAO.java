package com.demo.reservation.web.dao.custom;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface ReservationCustomDAO {

    boolean hasConflict(Long roomId, LocalDate day, List<Integer> cellSequences);

    List<Reservation> bulkInsert(List<Integer> cellSequences, Integer repeatCount, LocalDate day, Room room, User user);
}
