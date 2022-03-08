package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        System.out.println(manufacturerDao.create(new Manufacturer(null, "Bohdan", "Ukraine")));
        System.out.println(manufacturerDao.create(new Manufacturer(null, "Vadym", "Poland")));
        System.out.println(manufacturerDao.create(new Manufacturer(null, "Bob", "Ukraine")));
        System.out.println();
        System.out.println(manufacturerDao.get(1L).get());
        System.out.println();
        manufacturerDao.getAll().forEach(m -> System.out.println(m.toString()));
        System.out.println();
        System.out.println(manufacturerDao.update(new Manufacturer(1L, "notBohdan", "Ukraine")));
        System.out.println();
        System.out.println(manufacturerDao.delete(1L));
    }
}
