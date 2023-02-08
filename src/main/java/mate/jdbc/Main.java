package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector
            = Injector.getInstance(ManufacturerDao.class.getPackageName());

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("testName", "testCountry");
        manufacturerDao.create(manufacturer);
        manufacturer.setId(5L);
        manufacturerDao.get(manufacturer.getId());
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(manufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
