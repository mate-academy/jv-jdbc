package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(null, "Toyota", "Japan");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("Created manufactured: " + createdManufacturer);
        Manufacturer receivedManufacturer = manufacturerDao.get(manufacturer.getId()).get();
        System.out.println("Received manufacturer: " + receivedManufacturer);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
        System.out.println("Updated manufacturer: " + updatedManufacturer);
        System.out.println("Is manufacturer deleted: " + manufacturerDao.delete(1L));
    }
}
