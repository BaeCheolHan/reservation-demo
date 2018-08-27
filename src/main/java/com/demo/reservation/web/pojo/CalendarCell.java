package com.demo.reservation.web.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CalendarCell {

    private String        roomName;
    private Integer       sequence;
    private Boolean       occupied;
    private String        owner;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
