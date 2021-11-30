package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.get(7L));
        System.out.println(manufacturerDao.delete(20L));
        System.out.println(manufacturerDao.create(new Manufacturer("Tesla", "USA")));
        System.out.println(manufacturerDao.update(new Manufacturer(8L, "Skoda", "Czech republic")));
        System.out.println(manufacturerDao.create(new Manufacturer(1L,"Mercedes", "Germany")));
        System.out.println(manufacturerDao.delete(1L));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
