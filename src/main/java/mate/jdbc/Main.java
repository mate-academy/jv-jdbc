package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer firstCar = new Manufacturer("BMW2", "Germany2");
        Manufacturer secondCar = new Manufacturer("Renault", "France");
        Manufacturer thirdCar = new Manufacturer("Ford", "USA");
        manufacturerDao.create(firstCar);
        manufacturerDao.create(secondCar);
        manufacturerDao.create(thirdCar);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
