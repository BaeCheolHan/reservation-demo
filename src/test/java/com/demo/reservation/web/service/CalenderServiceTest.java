package com.demo.reservation.web.service;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.exception.InternalException;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.pojo.Calendar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class CalenderServiceTest {

    @MockBean
    private RoomService roomService;

    @MockBean
    private ReservationService reservationService;
    private CalenderService    calenderService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        calenderService = new CalenderService(reservationService, roomService);
    }

    @Test(expected = InternalException.class)
    public void test_emptyRooms() throws NoContentException, InternalException {

        given(roomService.findAll()).willThrow(new NoContentException("empty!"));

        calenderService.findDailyCalenderByDay(LocalDate.now());
    }

    @Test
    public void test_emptyCalendars() throws NoContentException {

        List<Room> testRooms = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Room room = new Room();
            room.setName(String.format("room-%c", 'A' + i));
            room.setId(i + 1L);
            testRooms.add(room);
        }

        given(roomService.findAll()).willReturn(testRooms);
        given(reservationService.findAllByDay(any())).willThrow(new NoContentException("empty!"));
        Calendar calendar = calenderService.findDailyCalenderByDay(LocalDate.now());

        Assert.assertEquals(4, calendar.getHeader().size());
        String[] expectedHeaders = { "Time Table", "room-A", "room-B", "room-C" };
        for (int i = 0; i < calendar.getHeader().size(); i++) {
            Assert.assertEquals(expectedHeaders[i], calendar.getHeader().get(i));
        }

        Assert.assertTrue(calendar.getRows().isEmpty());
    }

    // 여기서 검증하는것이 맞는가 ?
    @Test
    public void test_correct() throws NoContentException {
        List<Room> testRooms = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Room room = new Room();
            room.setName(String.format("room-%c", 'A' + i));
            room.setId(i + 1L);
            testRooms.add(room);
        }

        Reservation reservation = new Reservation();
        reservation.setRoom(testRooms.get(0));
        reservation.setRepeatCount(0);
        reservation.setRowSequence(0);
        reservation.setId(1L);

        given(roomService.findAll()).willReturn(testRooms);
        Calendar calendar = calenderService.findDailyCalenderByDay(LocalDate.now());
    }
}