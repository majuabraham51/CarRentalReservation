package com.crd.car.rental;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class RentalApplication {

    @PostConstruct
    public void executeAfterMain() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Europe/Berlin")));
    }

    public static void main(String[] args) {
        SpringApplication.run(RentalApplication.class,args);
    }
}
