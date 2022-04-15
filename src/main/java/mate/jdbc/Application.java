package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Application {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void run() {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Audi", "Germany");
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(1L);
        manufacturerDao.getAll();
        manufacturer.setName("Tesla");
        manufacturer.setCountry("USA");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(manufacturer.getId());
    }
}
