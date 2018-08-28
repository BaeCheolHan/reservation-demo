package com.demo.reservation.web.resource;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.IllegalBodyException;
import com.demo.reservation.web.pojo.request.ReservationCreateBody;
import com.demo.reservation.web.service.ReservationService;
import com.demo.reservation.web.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    private Logger             logger = LoggerFactory.getLogger(this.getClass());
    private ReservationService reservationService;

    public ReservationResource(ReservationService reservationService) {

        this.reservationService = reservationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public List<Reservation> create(@RequestBody @Valid ReservationCreateBody body) throws ConflictException, IllegalBodyException {

        logger.debug("reservation create > [{}]", body);
        boolean validTime = TimeUtils.validMinute(body.getStartTime()) && TimeUtils.validMinute(body.getEndTime());
        boolean validTimeRange = TimeUtils.validRange(body.getStartTime(), body.getEndTime());

        if (validTime && validTimeRange) {

            return reservationService.create(body);
        } else {

            String message = !validTime ? "entered invalid time values. 'minute' field required (00 or 30 )." : "entered invalid time range values. 'start time' must greater than 'end time'.";
            throw new IllegalBodyException(message);
        }
    }

}
