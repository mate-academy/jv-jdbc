package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer first = manufacturerDao.create(new Manufacturer("First", "Ukraine"));
        Manufacturer second = manufacturerDao.create(new Manufacturer("Second", "Ukraine"));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(first.getId()));
        second.setCountry("USA");
        System.out.println(manufacturerDao.update(second));
        Manufacturer third = manufacturerDao.create(new Manufacturer("Third", "Ukraine"));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(third.getId()));
        System.out.println(manufacturerDao.getAll());
    }
}
