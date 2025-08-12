package com.crd.car.rental.model;

import lombok.Getter;

@Getter
public abstract class Car {
    private final String type;

    public Car(String type) {
        this.type = type;
    }

}