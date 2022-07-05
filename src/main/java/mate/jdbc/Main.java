package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        System.out.println("CREATION:");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(null);
        manufacturer.setName("Ford Motor");
        manufacturer.setCountry("United States");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer savedManufacturerFirst = manufacturerDao.create(manufacturer);
        System.out.println(savedManufacturerFirst);
        manufacturer.setId(100L);
        manufacturer.setName("Toyota Motor");
        manufacturer.setCountry("Japan");
        System.out.println(manufacturerDao.create(manufacturer));
        System.out.println("RETRIEVING ONE:");
        Optional<Manufacturer> retrievedManufacturer = manufacturerDao.get(1L);
        System.out.println(retrievedManufacturer);
        System.out.println(manufacturerDao.get(null));
        System.out.println(manufacturerDao.get(1000L));
        System.out.println("RETRIEVING ALL:");
        List<Manufacturer> retrievedAllManufacturers = manufacturerDao.getAll();
        retrievedAllManufacturers.forEach(System.out::println);
        System.out.println("UPDATING:");
        manufacturer.setId(1L);
        manufacturer.setName("Hyundai Motor");
        manufacturer.setCountry("South Korea");
        Optional<Manufacturer> updatedManufacturer = manufacturerDao.update(manufacturer);
        System.out.println(updatedManufacturer);
        System.out.println("DELETING:");
        boolean deletedManufacturer = manufacturerDao.delete(1L);
        System.out.println(deletedManufacturer);
        System.out.println("RETRIEVING AND UPDATING AFTER DELETING:");
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.update(manufacturer));
    }
}
