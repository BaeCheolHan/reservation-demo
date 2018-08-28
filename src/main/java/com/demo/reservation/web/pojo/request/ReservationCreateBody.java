package com.demo.reservation.web.pojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ReservationCreateBody {

    @NotNull
    private Long roomId;

    @NotNull
    private Long userId;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate day;

    @NotNull
    private Integer repeatCount;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ReservationCreateBody body = (ReservationCreateBody) o;
        return Objects.equals(roomId, body.roomId) &&
                Objects.equals(userId, body.userId) &&
                Objects.equals(startTime, body.startTime) &&
                Objects.equals(endTime, body.endTime) &&
                Objects.equals(day, body.day) &&
                Objects.equals(repeatCount, body.repeatCount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roomId, userId, startTime, endTime, day, repeatCount);
    }
}
