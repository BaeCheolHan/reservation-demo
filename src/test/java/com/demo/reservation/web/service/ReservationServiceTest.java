package com.demo.reservation.web.service;

import com.demo.reservation.web.dao.ReservationDAO;
import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.pojo.request.ReservationCreateBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ReservationServiceTest {

    @MockBean
    private ReservationDAO dao;

    @MockBean
    private RoomService roomService;

    @MockBean
    private UserService userService;

    private ReservationService reservationService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationService(dao, roomService, userService);
    }

    @Test(expected = NoContentException.class)
    public void test_NoContent() throws NoContentException {

        given(dao.findAllByDay(any())).willReturn(new ArrayList<>());

        reservationService.findAllByDay(LocalDate.now());
    }

    @Test(expected = ConflictException.class)
    public void test_ConflictDuringValidateWithValues() throws ConflictException {

        given(dao.hasConflict(any(), any(), any())).willReturn(true);

        reservationService.create(0L, 0L, LocalDate.now(), LocalTime.now(), LocalTime.now(), 0);
    }

    @Test(expected = ConflictException.class)
    public void test_ConflictDuringValidateWithPojo() throws ConflictException {

        ReservationCreateBody body = new ReservationCreateBody();
        body.setRoomId(0L);
        body.setUserId(0L);
        body.setDay(LocalDate.now());
        body.setStartTime(LocalTime.now());
        body.setEndTime(LocalTime.now());
        body.setRepeatCount(0);

        given(dao.hasConflict(any(), any(), any())).willReturn(true);

        reservationService.create(body);
    }

    @Test(expected = ConflictException.class)
    public void test_ConflictDuringBulkInsertWithValues() throws ConflictException {

        given(dao.bulkInsert(any(), any(), any(), any(), any())).willThrow(new ConflictException(0, Reservation.class));

        reservationService.create(0L, 0L, LocalDate.now(), LocalTime.now(), LocalTime.now(), 0);
    }

    @Test(expected = ConflictException.class)
    public void test_ConflictDuringBulkInsertWithPojo() throws ConflictException {

        ReservationCreateBody body = new ReservationCreateBody();
        body.setRoomId(0L);
        body.setUserId(0L);
        body.setDay(LocalDate.now());
        body.setStartTime(LocalTime.now());
        body.setEndTime(LocalTime.now());
        body.setRepeatCount(0);

        given(dao.bulkInsert(any(), any(), any(), any(), any())).willThrow(new ConflictException(0, Reservation.class));

        reservationService.create(body);
    }
}