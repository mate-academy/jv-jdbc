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
        Manufacturer fordManufacturer = new Manufacturer("Ford Motor", "USA");
        Manufacturer porscheManufacturer = new Manufacturer("Porsche", "Germany");
        manufacturerDao.create(fordManufacturer);
        manufacturerDao.create(porscheManufacturer);
        Optional<Manufacturer> optionalManufacturer =
                manufacturerDao.get(fordManufacturer.getId());
        optionalManufacturer.ifPresent(System.out::println);
        porscheManufacturer.setName("Porsche");
        manufacturerDao.update(porscheManufacturer);
        manufacturerDao.delete(fordManufacturer.getId());
    }
}
