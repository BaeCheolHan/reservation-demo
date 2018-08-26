package com.demo.reservation.web.dao;

import com.demo.reservation.web.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDAO extends JpaRepository<Reservation, Long> {

}
