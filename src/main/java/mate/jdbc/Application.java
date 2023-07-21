package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Application {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void run() {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Jeep", "USA");
        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll();
        manufacturerDao.get(3L);
        manufacturer.setName("Toyota");
        manufacturer.setCountry("Japan");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(manufacturer.getId());
    }
}
