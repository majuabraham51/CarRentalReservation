package com.crd.car.rental.service;

import com.crd.car.rental.config.CarDetailsConfig;
import com.crd.car.rental.model.Reservation;
import com.crd.car.rental.model.ReservationCriteria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarReservationServiceTest {

    private static CarReservationService carReservationService;
    private static ZonedDateTime time;
    private static ReservationCriteria criteria;
    @BeforeAll
    static void setUp() {
        CarDetailsConfig props = new CarDetailsConfig();
        props.setInventory(Map.of(
                "sedan", 2,
                "suv", 1,
                "van", 1
        ));
        time= ZonedDateTime.now().plusSeconds(2);
        carReservationService = new CarReservationService(props);
        criteria = ReservationCriteria.builder()
                .carType("suv")
                .startDate(time)
                .days(3).build();
    }

    @Test
    void showReservationsTest() {

        carReservationService.book(criteria);
        List<Reservation> reservations = carReservationService.showReservations();
        assertEquals(1,reservations.size());

    }

    @Test
    void getBookedCountTest()  {
        int bookedCount = carReservationService.getBookedCount(criteria);
        assertEquals(1,bookedCount);
    }

    @Test
    void showInventoryTest()  {
        carReservationService.book(criteria);
        List<Reservation> reservations = carReservationService.showReservations();
        assertEquals(1,reservations.size());
    }

}