package com.crd.car.rental.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public record Reservation(Car car, ZonedDateTime startDate, int days) {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");

    @Override
    public String toString() {
        return "Reserved " + car.getType() + " from " + startDate.format(formatter) + " for " + days + " days.";
    }
    public boolean overlaps(ZonedDateTime requestStart, ZonedDateTime requestEnd) {
        return !(requestEnd.isBefore(startDate) || requestStart.isAfter(startDate.plusDays(days)));
    }
}

