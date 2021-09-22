package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;


public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturer.setName("Subaru");
        manufacturer.setCountry("Japan");
        // test other methods from ManufacturerDao
        // manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(20L));

    }
}
