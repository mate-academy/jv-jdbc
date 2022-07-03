package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer jeep = new Manufacturer("Jeep", "USA");

        manufacturerDao.create(mercedes);
        manufacturerDao.create(toyota);
        manufacturerDao.create(jeep);

        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(mercedes.getId()));
        System.out.println(manufacturerDao.delete(toyota.getId()));
        System.out.println(manufacturerDao.update(jeep));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
