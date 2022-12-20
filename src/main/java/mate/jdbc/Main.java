package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerIvan = new Manufacturer();
        manufacturerDao.create(manufacturerIvan);
        manufacturerDao.get(1L);
        manufacturerDao.update(manufacturerIvan);
        manufacturerDao.delete(1L);
        manufacturerDao.getAll();
    }
}
