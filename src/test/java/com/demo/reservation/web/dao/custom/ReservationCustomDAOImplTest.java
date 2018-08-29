package com.demo.reservation.web.dao.custom;

import com.demo.reservation.web.dao.ReservationDAO;
import com.demo.reservation.web.dao.RoomDAO;
import com.demo.reservation.web.dao.UserDAO;
import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class ReservationCustomDAOImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoomDAO roomDAO;

    private ReservationCustomDAOImpl daoImpl;

    private Room        sharedRoom;
    private User        sharedUser;
    private Reservation sharedReservationRow1;
    private Reservation sharedReservationRow2;

    @Before
    public void setUp() throws Exception {

        daoImpl = new ReservationCustomDAOImpl(entityManager);
        // init rooms
        Room room = new Room();
        room.setName(String.format("room-%c", 'A'));

        sharedRoom = roomDAO.save(room);

        // init user
        User user = new User();
        user.setName("Tester");
        sharedUser = userDAO.save(user);

        // init reservation
        Reservation reservation1 = new Reservation();
        reservation1.setRoom(sharedRoom);
        reservation1.setRepeatCount(1);
        //00:30 ~ 01:00
        reservation1.setRowSequence(1);
        reservation1.setDay(LocalDate.now());
        reservation1.setUser(sharedUser);

        Reservation reservation2 = new Reservation();
        reservation2.setRoom(sharedRoom);
        reservation2.setRepeatCount(1);
        //00:30 ~ 01:00
        reservation2.setRowSequence(2);
        reservation2.setDay(LocalDate.now());
        reservation2.setUser(sharedUser);
        sharedReservationRow1 = reservationDAO.save(reservation1);
        sharedReservationRow2 = reservationDAO.save(reservation2);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @After
    public void tearDown() throws Exception {

        reservationDAO.deleteAllInBatch();
        testEntityManager.flush();
        testEntityManager.clear();

    }

    @Test
    public void test_hasConflictWithNoneRepeat() {

        Assert.assertTrue(daoImpl.hasConflict(sharedRoom.getId(), LocalDate.now(), Collections.singletonList(1)));
        Assert.assertFalse(daoImpl.hasConflict(sharedRoom.getId(), LocalDate.now().plusDays(1), Collections.singletonList(1)));
    }

    @Test
    public void test_hasConflictWithRepeat() {

        //after one-day from shared reservation1, repeat 3 weeks.
        daoImpl.bulkInsert(Collections.singletonList(1), 3, LocalDate.now().plusDays(1), sharedRoom, sharedUser);

        //added reservation.
        Assert.assertTrue(daoImpl.hasConflict(sharedRoom.getId(), LocalDate.now().plusDays(1), Collections.singletonList(1)));
        //doesn't exist.
        Assert.assertFalse(daoImpl.hasConflict(sharedRoom.getId(), LocalDate.now().plusDays(2), Collections.singletonList(1)));

        //next week
        Assert.assertTrue(daoImpl.hasConflict(sharedRoom.getId(), LocalDate.now().plusWeeks(1).plusDays(1), Collections.singletonList(1)));
        Assert.assertFalse(daoImpl.hasConflict(sharedRoom.getId(), LocalDate.now().plusWeeks(1), Collections.singletonList(1)));
    }

    @Test
    public void test_bulkInsertWithRepeat() {

        // remove shared reservation1, 2
        reservationDAO.deleteAllInBatch();

        daoImpl.bulkInsert(Collections.singletonList(1), 3, LocalDate.now(), sharedRoom, sharedUser);
        List<Reservation> result = reservationDAO.findAll();

        // repeat (3)
        Assert.assertEquals(3, result.size());

        LocalDate expectedDay = LocalDate.now();
        // require interval : 1 week
        for (Reservation reservation : result) {
            Assert.assertEquals(expectedDay, reservation.getDay());
            expectedDay = expectedDay.plusWeeks(1);
        }
    }

}