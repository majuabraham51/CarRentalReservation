package com.crd.car.rental.factory;

import com.crd.car.rental.exception.CarNotFoundException;
import com.crd.car.rental.model.Car;
import com.crd.car.rental.model.SUV;
import com.crd.car.rental.model.Sedan;
import com.crd.car.rental.model.Van;

public class CarFactory {

    public static Car createCarForRental(String typeOfCar){

        return switch (typeOfCar.toLowerCase()) {
            case "sedan" -> new Sedan();
            case "suv" -> new SUV();
            case "van" -> new Van();
            default -> throw new CarNotFoundException("This car "+ typeOfCar+" Not available with us stay Tuned..");
        };
    }
}
