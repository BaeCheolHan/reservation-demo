package com.demo.reservation.web.dao;

import com.demo.reservation.web.entity.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRoomDAO extends JpaRepository<ConferenceRoom, Long> {
}
