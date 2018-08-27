package com.demo.reservation.web.resource;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.IllegalBodyException;
import com.demo.reservation.web.pojo.request.ReservationCreateBody;
import com.demo.reservation.web.service.ReservationService;
import com.demo.reservation.web.util.TimeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationResource {

    private ReservationService reservationService;

    public ReservationResource(ReservationService reservationService) {

        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public Reservation findById(@PathVariable("id") Long id) {

        return reservationService.findById(id);
    }

    @GetMapping
    public List<Reservation> findAll() {

        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody @Valid ReservationCreateBody body) throws ConflictException, IllegalBodyException {

        System.out.println(body);
        boolean validTime = TimeUtils.validMinute(body.getStartTime()) && TimeUtils.validMinute(body.getEndTime());
        boolean validTimeRange = TimeUtils.validRange(body.getStartTime(), body.getEndTime());

        if (validTime && validTimeRange) {
            reservationService.create(body);
        } else {
            throw new IllegalBodyException(String.format("[validTimeField:%b / validTimeRange:%b] entered invalid time values.", validTime, validTimeRange));
        }
    }

}
