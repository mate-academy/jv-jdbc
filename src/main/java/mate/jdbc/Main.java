package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer chryslerCar = new Manufacturer("Chrysler", "USA");
        Manufacturer hondaCar = new Manufacturer("Honda", "Japan");
        manufacturerDao.create(chryslerCar);
        manufacturerDao.create(hondaCar);
        chryslerCar.setCountry("Italy");
        manufacturerDao.update(chryslerCar);
        manufacturerDao.delete(hondaCar.getId());
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
