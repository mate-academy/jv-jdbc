package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.manufacturer.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer car1 = new Manufacturer("Chrysler", "USA");
        Manufacturer car2 = new Manufacturer("Honda", "Japan");
        manufacturerDao.create(car1);
        manufacturerDao.create(car2);
        manufacturerDao.update(car1);
        manufacturerDao.delete(car2.getId());
        manufacturerDao.getAll();

    }
}
