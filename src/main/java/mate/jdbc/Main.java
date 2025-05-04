package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        // Create
        System.out.println("Creating...");
        Manufacturer toyotaManufacturer = new Manufacturer("Toyota", "Japan");
        manufacturerDao.create(toyotaManufacturer);
        Manufacturer volkswagenManufacturer = new Manufacturer("Volkswagen", "Germany");
        manufacturerDao.create(volkswagenManufacturer);
        Manufacturer daimlerManufacturer = new Manufacturer("Diamler", "Germany");
        manufacturerDao.create(daimlerManufacturer);
        Manufacturer fordMotorManufacturer = new Manufacturer("Ford Motor", "Untied States");
        manufacturerDao.create(fordMotorManufacturer);
        manufacturerDao.getAll().stream().forEach(System.out::println);
        // Delete
        System.out.println("Deleting...");
        manufacturerDao.delete(toyotaManufacturer.getId());
        manufacturerDao.delete(volkswagenManufacturer.getId());
        manufacturerDao.getAll().stream().forEach(System.out::println);
        // Update
        System.out.println("Updating...");
        daimlerManufacturer.setName("Daimler");
        fordMotorManufacturer.setCountry("United States");
        manufacturerDao.update(daimlerManufacturer);
        manufacturerDao.update(fordMotorManufacturer);
        manufacturerDao.getAll().stream().forEach(System.out::println);
        // Clear after tests
        manufacturerDao.getAll().stream().forEach(m -> manufacturerDao.delete(m.getId()));
    }
}
