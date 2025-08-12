package com.crd.car.rental.service;

import com.crd.car.rental.config.CarDetailsConfig;
import com.crd.car.rental.exception.CarNotFoundException;
import com.crd.car.rental.model.Reservation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarReservationServiceTest {

    private static CarReservationService carReservationService;

    @BeforeAll
    static void setUp() {
        CarDetailsConfig props = new CarDetailsConfig();
        props.setInventory(Map.of(
                "sedan", 2,
                "suv", 1,
                "van", 1
        ));

        CarInventoryService carInventoryService = new CarInventoryService(props);
        carReservationService = new CarReservationService(carInventoryService);
    }


    @ParameterizedTest
    @ValueSource(strings = {"sedan", "suv", "van"})
    @Order(1)
    void reserveAllTypeCarTest(String allCarType) {
        assertDoesNotThrow(() -> {
            carReservationService.reserveCar(allCarType, ZonedDateTime.now(), 3);
        });

    }

    @Test
    @Order(2)
    void reserveSedanTypeCarTest() {
        assertTrue(carReservationService.reserveCar("sedan", ZonedDateTime.now(), 2));
    }


    @Test
    @Order(3)
    void reserveSedanTypeCarCarOutOFStockExceptionScenarioTest() {
        assertFalse(carReservationService.reserveCar("sedan", ZonedDateTime.now(), 1));
    }

    @Test
    void reserveCarNotAvailableScenarioTest() {
        assertFalse(carReservationService.reserveCar("suv", ZonedDateTime.now(), 3));
    }

    @Test
    @Order(4)
    void reserveCarNonExistentExceptionScenarioTest() {

        CarNotFoundException dummyCar = Assertions.assertThrows(CarNotFoundException.class, () -> {
            carReservationService.reserveCar("DummyCar", ZonedDateTime.now(), 3);
        });
        assert (dummyCar.getMessage().equals("This car DummyCar Not available with us stay Tuned.."));
    }

    @Test
    @Order(5)
    void reserveCarValidationPastDateScenarioTest() {

        IllegalArgumentException exception = Assertions
                .assertThrows(IllegalArgumentException.class, () -> {
            carReservationService
                    .reserveCar("suv", ZonedDateTime.now().minusHours(1), 3);
        });
        assert (exception.getMessage().equals("Either one of condition is not met --> days>0 || startDate is in the past || car type is blank"));
    }


/*
    Expected count 4
    Reserved Sedan from 07-08-2025 13:33:10 CEST for 3 days.
    Reserved SUV from 07-08-2025 13:33:10 CEST for 3 days.
    Reserved VAN from 07-08-2025 13:33:10 CEST for 3 days.
    Reserved Sedan from 07-08-2025 13:33:10 CEST for 2 days.*/

    @Test
    @Order(4)
    void showReservationsTest() {
        List<Reservation> reservations = carReservationService.showReservations();
        assertEquals(4,reservations.size());

    }

}