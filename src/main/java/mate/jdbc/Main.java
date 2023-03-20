package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerBmw = new Manufacturer("BMW", "Germany");
        manufacturerDao.create(manufacturerBmw);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(7L));
        Manufacturer manufacturerFord = new Manufacturer(8L, "Ford", "USA");
        manufacturerDao.update(manufacturerFord);
        System.out.println(manufacturerDao.get(8L));
        manufacturerDao.delete(6L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
