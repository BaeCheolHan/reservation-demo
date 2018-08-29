package com.demo.reservation.web.service;

import com.demo.reservation.web.dao.ReservationDAO;
import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.entity.User;
import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.pojo.request.ReservationCreateBody;
import com.demo.reservation.web.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private Logger         logger = LoggerFactory.getLogger(this.getClass());
    private ReservationDAO dao;
    private RoomService    roomService;
    private UserService    userService;

    public ReservationService(ReservationDAO dao, RoomService roomService, UserService userService) {

        this.dao = dao;
        this.roomService = roomService;
        this.userService = userService;
    }

    public List<Reservation> findAllByDay(LocalDate day) throws NoContentException {

        List<Reservation> result = dao.findAllByDay(day);
        if (CollectionUtils.isEmpty(result)) {
            throw new NoContentException("empty!");
        }

        return result;
    }

    public List<Reservation> create(ReservationCreateBody body) {

        logger.debug("reservation create > [{}]", body);
        return create(body.getRoomId(), body.getUserId(), body.getDay(), body.getStartTime(), body.getEndTime(), body.getRepeatCount());
    }

    public List<Reservation> create(Long roomId, Long userId, LocalDate day, LocalTime startTime, LocalTime endTime, Integer repeatCount) {

        List<Integer> rowSequences = TimeUtils.getTimeTableSequence(startTime, endTime);

        for (Integer week = 0; week <= repeatCount; week++) {
            final LocalDate finalDay = day.plusWeeks(week);
            if (dao.hasConflict(roomId, finalDay, rowSequences)) {
                throw new ConflictException(roomId, Reservation.class);
            }
        }

        Room room = roomService.findById(roomId);
        User user = userService.findById(userId);
        return dao.bulkInsert(rowSequences, repeatCount, day, room, user);

    }
}
