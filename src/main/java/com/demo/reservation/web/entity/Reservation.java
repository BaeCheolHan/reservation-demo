package com.demo.reservation.web.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Reservation", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "day", "rowSequence", "room_id" }) }
)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @NotNull
    private Room room;

    @ManyToOne
    @NotNull
    private User user;

    @Column(nullable = false)
    private LocalDate day;

    @Column(nullable = false)
    private Integer rowSequence;

    @Column(nullable = false)
    private Integer repeatCount;
}
