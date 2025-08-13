package com.crd.car.rental.service;

import com.crd.car.rental.config.CarDetailsConfig;
import com.crd.car.rental.factory.CarFactory;
import com.crd.car.rental.model.Car;
import com.crd.car.rental.model.Reservation;
import com.crd.car.rental.model.ReservationCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CarReservationService {

    private static final Logger log = LoggerFactory.getLogger(CarReservationService.class);
    private final Map<String, Integer> inventory;
    private final List<Reservation> reservations = new ArrayList<>();

    // Loading all the inventory to Memory
    public CarReservationService(CarDetailsConfig config) {
        this.inventory = new ConcurrentHashMap<>(config.getInventory()); //  from properties
    }

    public void book(ReservationCriteria reservationCriteria) {

        Car car = CarFactory.createCarForRental(reservationCriteria.getCarType());
        reservations.add(new Reservation(car, reservationCriteria.getStartDate(), reservationCriteria.getDays()));
    }

    public boolean isAvailable(ReservationCriteria reservationCriteria) {
        int bookedCount = getBookedCount(reservationCriteria);
        int total = inventory.getOrDefault(reservationCriteria.getCarType().toLowerCase(), 0);
        return bookedCount < total;
    }

    public int getAvailableCount(ReservationCriteria reservationCriteria) {
        int bookedCount = getBookedCount(reservationCriteria);
        int total = inventory.getOrDefault(reservationCriteria.getCarType().toLowerCase(), 0);
        return total - bookedCount;
    }

    public int getBookedCount(ReservationCriteria reservationCriteria) {
        return (int) reservations.stream()
                .filter(r -> r.car().getType().equalsIgnoreCase(reservationCriteria.getCarType()))
                .filter(r -> r.overlaps(reservationCriteria.getStartDate(), reservationCriteria.getStartDate().plusDays(reservationCriteria.getDays())))
                .count();
    }

    public List<Reservation> showReservations() {
        log.info("All Reservations:");
        reservations.forEach(System.out::println);
        return reservations;
    }

    public void showInventory() {
        log.info("Inventory: {}", inventory);
    }
}
