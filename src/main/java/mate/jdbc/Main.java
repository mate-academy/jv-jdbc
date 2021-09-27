package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static Manufacturer manufacturer;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                    .getInstance(ManufacturerDao.class);
        manufacturer = new Manufacturer((long) 11, "Tesla", "USA");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer((long) 11, "Tesla", "THE US");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete((long) 7);
        manufacturerDao.get((long) 11);
        manufacturerDao.getAll().stream()
                .forEach(System.out::println);
    }
}
