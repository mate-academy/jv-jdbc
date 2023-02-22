package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("*************** list all manufacturers *******************");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("*************** create new manufacturers *******************");
        Manufacturer testManufacturer = new Manufacturer(null, "testManufacturer", "testCountry");
        Manufacturer newManufacturer = manufacturerDao.create(testManufacturer);
        manufacturerDao.get(newManufacturer.getId()).ifPresent(System.out::println);
        System.out.println("*************** get&update manufacturers *******************");
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(newManufacturer.getId());
        if (optionalManufacturer.isPresent()) {
            testManufacturer = optionalManufacturer.get();
            testManufacturer.setCountry("Ukraine");
            testManufacturer = manufacturerDao.update(testManufacturer);
            manufacturerDao.get(testManufacturer.getId()).ifPresent(System.out::println);
        }
        System.out.println("*************** delete manufacturers *******************");
        manufacturerDao.delete(testManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
