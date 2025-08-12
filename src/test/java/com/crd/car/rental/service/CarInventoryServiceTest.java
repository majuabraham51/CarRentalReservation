package com.crd.car.rental.service;

import com.crd.car.rental.config.CarDetailsConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarInventoryServiceTest {

    private static CarInventoryService carInventoryService;

    @BeforeAll
    static void setUp() {
        CarDetailsConfig props = new CarDetailsConfig();
        props.setInventory(Map.of(
                "sedan", 2,
                "suv", 1,
                "van", 1
        ));

        carInventoryService = new CarInventoryService(props);
    }


    @Test
    void reserveTest() {
        boolean sedanStatus = carInventoryService.reserve("sedan");
        assertTrue(sedanStatus);
    }
    @Test
    void reserveCarNotExistTest() {
        boolean carNotExist = carInventoryService.reserve("Test");
        assertFalse(carNotExist);

    }

    @Test
    void reserveCarNullExceptionTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            carInventoryService.reserve(" ");
        });

        assert(exception.getMessage().equals("carType should not be null"));

    }

    @Test
    void showInventoryTest() {
        carInventoryService.showInventory();
    }
}