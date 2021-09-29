package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("testIn", "Ukraine");
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(9L);
        manufacturerDao.update(new Manufacturer(9L,
                "manufacturer", "Poland"));
        manufacturerDao.getAll();
        manufacturerDao.delete(7L);
    }
}
