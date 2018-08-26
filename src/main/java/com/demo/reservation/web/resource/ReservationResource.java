package com.demo.reservation.web.resource;

import com.demo.reservation.web.entity.Reservation;
import com.demo.reservation.web.service.ReservationService;
import org.springframework.web.bind.annotation.*;

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

    ;

    @PostMapping
    public Reservation create() {

        return null;
    }

}
