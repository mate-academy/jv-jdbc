package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao
                manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer testManufacturer1 = new Manufacturer(2L, "Audi", "Germany");
        Manufacturer testManufacturer2 = new Manufacturer("BMW", "Germany");

        System.out.println(manufacturerDao.create(testManufacturer1));
        System.out.println(manufacturerDao.get(testManufacturer2.getId()));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.update(testManufacturer2));
        System.out.println(manufacturerDao.delete(testManufacturer1.getId()));
    }
}
