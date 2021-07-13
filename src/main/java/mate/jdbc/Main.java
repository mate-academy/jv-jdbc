package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmwCarManufacturer = new Manufacturer("BMW", "Germany");
        Manufacturer fordCarManufacturer = new Manufacturer("Ford", "USA");
        Manufacturer volkswagenCarManufacturer = new Manufacturer("Volkswagen", "Germany");

        Manufacturer newBmwCarManufacturer = manufacturerDao.create(bmwCarManufacturer);
        Manufacturer newFordCarManufacturer = manufacturerDao.create(fordCarManufacturer);
        Manufacturer newVolkswagenCarManufacturer = manufacturerDao.create(volkswagenCarManufacturer);

        System.out.println(manufacturerDao.update(newBmwCarManufacturer));
        System.out.println(manufacturerDao.update(newVolkswagenCarManufacturer));
        System.out.println(manufacturerDao.update(newFordCarManufacturer));

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
