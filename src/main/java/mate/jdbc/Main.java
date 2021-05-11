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
        manufacturer.setId(2L);
        manufacturer.setName("UpdatedName");
        manufacturer.setCountry("UpdatedCountry");

        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(1L);
        manufacturerDao.delete(manufacturerOptional.get().getId());
        manufacturerDao.update(manufacturer);
        manufacturerDao.create(manufacturerOptional.get());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
