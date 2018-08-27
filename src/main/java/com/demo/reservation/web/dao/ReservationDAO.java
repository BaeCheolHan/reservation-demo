package com.demo.reservation.web.dao;

import com.demo.reservation.web.dao.custom.ReservationCustomDAO;
import com.demo.reservation.web.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Long>, ReservationCustomDAO {

    List<Reservation> findAllByDay(LocalDate day);
}
