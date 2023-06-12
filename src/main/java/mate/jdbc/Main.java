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
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Bohdan");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(0L);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturer.setName("LAZ");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(0L);
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
