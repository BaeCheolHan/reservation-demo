package com.demo.reservation.web.service;

import com.demo.reservation.web.dao.ConferenceRoomDAO;
import com.demo.reservation.web.entity.ConferenceRoom;
import com.demo.reservation.web.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ConferenceRoomService {

    private ConferenceRoomDAO dao;

    public ConferenceRoomService(ConferenceRoomDAO dao) {
        this.dao = dao;
    }

    public ConferenceRoom findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new NotFoundException(id, ConferenceRoom.class));
    }

    @PostConstruct
    public void initRooms() {
        // set Room
        for (int i = 0; i < 10; i++) {
            ConferenceRoom room = new ConferenceRoom();
            room.setName(String.format("room-%c", 'A' + i));
            dao.save(room);
        }

        dao.findAll().forEach(System.out::println);
    }
}
