package com.crd.car.rental.service;

import com.crd.car.rental.factory.CarFactory;
import com.crd.car.rental.model.Car;
import com.crd.car.rental.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarReservationService {

    private static final Logger log = LoggerFactory.getLogger(CarReservationService.class);
    private final CarInventoryService inventory;
    private final List<Reservation> reservations = new ArrayList<>();

    public CarReservationService(CarInventoryService inventory) {
        this.inventory = inventory;
    }

    public boolean reserveCar(String typeOfCar, ZonedDateTime startDate, int days) {

        if (days < 0 || startDate.isBefore(ZonedDateTime.now()) || typeOfCar.isBlank())
            throw new IllegalArgumentException("Either one of condition is not met --> days>0 || startDate is in the past || car type is blank");

        Car car = CarFactory.createCarForRental(typeOfCar);

        if (inventory.reserve(typeOfCar)) {
            Reservation reservation = new Reservation(car, startDate, days);
            reservations.add(reservation);
            log.info("Reserved: {}", reservation);

            return true;
        } else {
            Optional<Reservation> reservation = reservations
                    .stream()
                    .filter(i -> i.car().getType().toLowerCase().equals(typeOfCar))
                    .findFirst();

            //No such car available in our list
           if (reservation.isEmpty())
                return false;

            //Suggesting when is the next available date is to book the requested car
            Reservation reservationWindow = reservation.get();
            String nextAvailableDate = reservationWindow.startDate().plusDays(reservationWindow.days()).format(Reservation.formatter);
            log.info("No {} cars Currently available till {}... you can book after this  ", typeOfCar, nextAvailableDate);

            return false;
            // throw new CarNotFoundException("This car Not available with us stay Tuned.!  type: " + typeOfCar);
        }
    }

    public List<Reservation> showReservations() {
        log.info("All Reservations:");
        reservations.forEach(System.out::println);
        return reservations;
    }
}
