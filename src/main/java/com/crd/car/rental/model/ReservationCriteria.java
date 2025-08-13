package com.crd.car.rental.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Builder
public class ReservationCriteria {
    private String carType;
    private ZonedDateTime startDate;
    private int days;


}
