package mate.jdbc;

import mate.jdbc.lib.Injector;
import model.dao.ManufacturerDao;
import model.entity.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturerDao.create(manufacturer);
    }
}
