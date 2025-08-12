package com.crd.car.rental.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "car")
public class CarDetailsConfig {
    private Map<String, Integer> inventory;

}
