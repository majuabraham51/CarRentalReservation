package com.crd.car.rental.service;

import com.crd.car.rental.config.CarDetailsConfig;
import com.crd.car.rental.exception.CarNotFoundException;
import com.crd.car.rental.model.ReservationCriteria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarRentalServiceTest {

    private static CarRentalService carRentalService;
    static ZonedDateTime time;
    @BeforeAll
    static void setUp() {
        CarDetailsConfig props = new CarDetailsConfig();
        props.setInventory(Map.of(
                "sedan", 2,
                "suv", 1,
                "van", 1
        ));
        time= ZonedDateTime.now();
        carRentalService = new CarRentalService(new CarReservationService(props));
    }

    @Test
    void reserveSedanTypeCarTest() {
        ReservationCriteria criteria = ReservationCriteria.builder()
                .carType("sedan")
                .startDate(time.plusMinutes(1))
                .days(2).build();
        System.out.println(carRentalService.rentCar(criteria));
        System.out.println(carRentalService.rentCar(criteria));
        System.out.println(carRentalService.rentCar(criteria));
    }

    @Test
    void reserveTest() {
        ReservationCriteria criteria = ReservationCriteria.builder()
                .carType("sedan")
                .startDate(time.plusSeconds(2))
                .days(1)
                .build();

        boolean sedanStatus = carRentalService.rentCar(criteria);
        assertTrue(sedanStatus);
    }

    @Test
    void reserveCarNotExistTest() {
        ReservationCriteria criteria = ReservationCriteria.builder()
                .carType("Test")
                .days(1)
                .startDate(time.plusSeconds(3))
                .build();

        Exception exception = assertThrows(CarNotFoundException.class, () -> {
            carRentalService.rentCar(criteria);
        });
        assert (exception.getMessage().equals("This car Test Not available with us stay Tuned.."));
    }

    @Test
    void reserveCarNullExceptionTest() {
        ReservationCriteria criteria = ReservationCriteria.builder()
                .carType(" ")
                .startDate(time.plusSeconds(3))
                .days(1)
                .build();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            carRentalService.rentCar(criteria);
        });

        assert (exception.getMessage().equals("Car type must not be null or blank"));

    }

}