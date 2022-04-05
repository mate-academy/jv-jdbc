package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.models.Car;
import mate.jdbc.models.Driver;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        //Init
        Injector injector = null;
        Injector instance = injector.getInstance("mate.jdbc.models");
        Car car = (Car) instance.getInstance(Car.class);
        Driver driver = (Driver) instance.getInstance(Car.class);
        Manufacturer manufacturer = (Manufacturer) instance.getInstance(Car.class);
    }
}

