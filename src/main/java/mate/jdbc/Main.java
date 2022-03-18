package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("NazikInc", "Ukraine");
        Manufacturer manufacturerTest = new Manufacturer("Apple", "USA");
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
        System.out.println(manufacturerDao.get(1L));
        manufacturer.setName("Samsung");
        manufacturer.setCountry("South Korea");
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(17L);
        manufacturerDao.get(17L);
    }
}
