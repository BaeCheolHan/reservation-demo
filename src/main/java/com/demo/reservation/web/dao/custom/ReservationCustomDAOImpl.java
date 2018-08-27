package com.demo.reservation.web.dao.custom;

import com.demo.reservation.web.entity.QReservation;
import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.entity.Room;
import com.demo.reservation.web.entity.User;
import com.demo.reservation.web.exception.ConflictException;
import com.mysema.query.jpa.impl.JPAQuery;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class ReservationCustomDAOImpl extends BaseCustomDAO implements ReservationCustomDAO {

    public ReservationCustomDAOImpl(EntityManager entityManager) {

        super(entityManager);
    }

    @Override
    public boolean hasConflict(Long roomId, LocalDate day, List<Integer> cellSequences) {

        QReservation reservation = QReservation.reservation;
        JPAQuery query = new JPAQuery(super.entityManager);

        query.from(reservation)
                .where(reservation.day.eq(day)
                        .and(reservation.room.id.eq(roomId))
                        .and(reservation.cellSequence.in(cellSequences))
                );

        return query.count() > 0;
    }

    @Transactional
    @Override
    public void bulkInsert(List<Integer> cellSequences, Integer repeatCount, LocalDate day, Room room, User user) {

//        super.entityManager.getTransaction().begin();
        try {

            for (Integer week = 0; week <= repeatCount; week++) {
                final LocalDate finalDay = day.plusWeeks(week);

                cellSequences.forEach(sequence -> {
                    Reservation reservation = new Reservation();
                    reservation.setRoom(room);
                    reservation.setDay(finalDay);
                    reservation.setUser(user);
                    reservation.setRepeatCount(repeatCount);
                    reservation.setCellSequence(sequence);
                    super.entityManager.persist(reservation);
                });

                entityManager.flush();
                entityManager.clear();
            }

        } catch (ConstraintViolationException e) {
            // 동시에 Insert 되어 UK 충돌이 날 경우, 409 처리
//            entityManager.getTransaction().rollback();
            System.out.println("what the!" + e.getMessage());
            throw new ConflictException(room.getId(), Reservation.class);
        } finally {

//            entityManager.close();
        }
        System.out.println("insert~");

    }
}
