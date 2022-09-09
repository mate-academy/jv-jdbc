package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(null, "Test name", "Test country");
        // initialize field values using setters or constructor
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
        System.out.println("Created manufacturer: " + manufacturer);
        manufacturer.setName("New name");
        manufacturer.setCountry("New country");
        manufacturerDao.update(manufacturer);
        System.out.println("Updated manufacturer: " + manufacturer);
        Optional<Manufacturer> manufacturer1 = manufacturerDao.get(manufacturer.getId());
        System.out.println("Manufacturer by code 1: " + manufacturer1);
        manufacturerDao.delete(manufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
        Optional<Manufacturer> manufacturer2 = manufacturerDao.get(12345L);
        System.out.println("Manufacturer by code 12335: " + manufacturer2);
    }
}
