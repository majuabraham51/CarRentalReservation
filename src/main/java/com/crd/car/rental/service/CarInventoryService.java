package com.crd.car.rental.service;

import com.crd.car.rental.config.CarDetailsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CarInventoryService {

    Logger log = LoggerFactory.getLogger(CarInventoryService.class);

    private final Map<String, Integer> inventory;

    public CarInventoryService(CarDetailsConfig config) {
        this.inventory = new ConcurrentHashMap<>(config.getInventory()); //  from properties
    }


    public boolean reserve(String carType) {
        if (carType.isBlank())
            throw new IllegalArgumentException("carType should not be null");

        var available = inventory.getOrDefault(carType, 0);
        if (available > 0) {
            inventory.put(carType, available - 1);
            return true;
        }

        log.debug("car type not present {}", carType);
        return false;
    }

    public void showInventory() {
        log.info("Inventory: {}", inventory);
    }
}
