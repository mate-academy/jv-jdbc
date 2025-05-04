package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static Injector injector = Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
    private static Manufacturer manufacturer;
    private static List<Manufacturer> allManufacturers;

    public static void main(String[] args) {
        // Add items in the table DB:
        manufacturer = new Manufacturer("Tesla", "USA");
        System.out.println(manufacturerDao.create(manufacturer).getId());
        manufacturer = new Manufacturer("Lincoln", "USA");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer("Rolls Royce", "UK");
        manufacturerDao.create(manufacturer);
        // Show all items in the table DB:
        allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        // Show all items in the table DB:
        allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        // Is deleted instance in DB:
        manufacturerDao.delete(15L);
        // Show all items in the table DB:
        allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        // Show one instance:
        Optional<Manufacturer> receivedOptional = manufacturerDao.get(11L);
        System.out.println(receivedOptional.get());
        // Update instance:
        manufacturer = new Manufacturer(16L, "Jaguar", "UK");
        System.out.println(manufacturerDao.update(manufacturer));
    }
}
