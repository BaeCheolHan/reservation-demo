package com.demo.reservation.web.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cell {

    private Long    roomId;
    private Integer rowSequence;
    private String  value;
}
