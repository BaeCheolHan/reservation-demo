package com.demo.reservation.web.service;

import com.demo.reservation.web.dao.RoomDAO;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RoomService {

    private RoomDAO dao;

    public RoomService(RoomDAO dao) {

        this.dao = dao;
    }

    public List<Room> findAll() throws NoContentException {

        List<Room> result = dao.findAll();

        if (CollectionUtils.isEmpty(result)) {
            throw new NoContentException("empty!");
        }
        return result;
    }

    public Room findById(Long id) {

        return dao.findById(id).orElseThrow(() -> new NotFoundException(id, Room.class));
    }

    @PostConstruct
    public void initRooms() {

        try {
            for (int i = 0; i < 10; i++) {
                Room room = new Room();
                room.setName(String.format("room-%c", 'A' + i));
                dao.save(room);
            }

        } catch (Exception e) {
            // ignore, already init room.
        }

    }
}
