package com.demo.reservation.web.dao;

import com.demo.reservation.web.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDAO extends JpaRepository<Room, Long> {

}
