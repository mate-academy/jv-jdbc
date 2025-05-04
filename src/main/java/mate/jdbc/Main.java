package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");
        Manufacturer honda = new Manufacturer();
        honda.setName("Honda");
        honda.setCountry("Poland");
        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println("Create manufacturer: " + mercedes.getName());
        manufacturerDao.create(mercedes);
        System.out.println("Create manufacturer: " + honda.getName());
        manufacturerDao.create(honda);
        System.out.println("Create manufacturer: " + toyota.getName());
        manufacturerDao.create(toyota);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturer list:");
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println("Update manufacturer: " + honda.getName());
        honda.setCountry("Japan");
        manufacturerDao.update(honda);

        System.out.println("Updated data :");
        allManufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturer list:");
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println("Delete manufacturer with id: " + toyota.getId());
        manufacturerDao.delete(toyota.getId());
        allManufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturer list:");
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }
    }
}
