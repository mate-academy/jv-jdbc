package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer =
                new Manufacturer("Panasonic", "Japan");
        manufacturerDao.create(manufacturer);
        manufacturer.setCountry("Ukraine");
        manufacturerDao.update(manufacturer);
        manufacturerDao.get(2L);
        manufacturerDao.delete(4L);
        manufacturerDao.getAll();

    }
}
