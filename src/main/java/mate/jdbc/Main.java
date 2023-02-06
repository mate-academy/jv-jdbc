package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturer.setName("Peugeot");
        manufacturer.setCountry("Hungary");
        manufacturer.setId(2L);
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.update(manufacturer);
        // test other methods from ManufacturerDao
    }
}
