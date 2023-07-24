package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
    }
}
