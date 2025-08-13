package com.crd.car.rental.service;

import com.crd.car.rental.factory.CarFactory;
import com.crd.car.rental.model.Car;
import com.crd.car.rental.model.ReservationCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
public class CarRentalService {

    private final CarReservationService carReservationService;

    public CarRentalService(CarReservationService carReservationService) {
        this.carReservationService = carReservationService;
    }

    public boolean rentCar(ReservationCriteria reservationCriteria) {

        //Validating Inputs
        validateReservationInput(reservationCriteria);

        Car car = CarFactory.createCarForRental(reservationCriteria.getCarType());
        if (!carReservationService.isAvailable(reservationCriteria)) {
            int available = carReservationService.getAvailableCount(reservationCriteria);
            log.info("Requested Car Model  : {} not available during that <from-to> time. (Available: {})",reservationCriteria.getCarType(),available);
            return false;
        }

        carReservationService.book(reservationCriteria);
        int remaining = carReservationService.getAvailableCount(reservationCriteria);
        log.info("Car booked: {} Remaining: {}", car.getType(), remaining);

        return true;
    }

    public void validateReservationInput(ReservationCriteria reservationCriteria) {
        if (reservationCriteria.getDays() < 0)
            throw new IllegalArgumentException("Days must be non-negative");
        if (reservationCriteria.getStartDate() == null||reservationCriteria.getStartDate().isBefore(ZonedDateTime.now()))
            throw new IllegalArgumentException("Start date must be in the future & not null");
        if (reservationCriteria.getCarType() == null || reservationCriteria.getCarType().isBlank())
            throw new IllegalArgumentException("Car type must not be null or blank");
    }
}
