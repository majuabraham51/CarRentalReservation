package com.crd.car.rental.service.unittest;

import com.crd.car.rental.service.CarInventoryService;
import com.crd.car.rental.service.CarReservationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarReservationServiceUnitTest {
    private static CarReservationService carReservationService;
    private static CarInventoryService mockCarInventoryService;

    @BeforeAll
    static void init(){
         mockCarInventoryService = Mockito.mock(CarInventoryService.class);
         carReservationService = new CarReservationService(mockCarInventoryService);

    }
    @Test
    void reserveTest() {
        Mockito.when(mockCarInventoryService.reserve("sedan")).thenReturn(true);
        boolean sedanStatus = carReservationService.reserveCar("sedan", ZonedDateTime.now(), 1);
        assertTrue(sedanStatus);
    }
    @Test
    void reserveNotPresentTest() {
        Mockito.when(mockCarInventoryService.reserve("sedan")).thenReturn(false);
        boolean sedanStatus = carReservationService.reserveCar("sedan", ZonedDateTime.now(), 1);
        assertFalse(sedanStatus);
    }
    @Test
    void showReservations() {
        carReservationService.showReservations();
    }
}