package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final long SOME_RANDOM_ID_FIRST = 1L;
    private static final long SOME_RANDOM_ID_SECOND = 2L;
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        manufacturerDao.create(new Manufacturer("Honda", "Kyiv"));
        manufacturerDao.create(new Manufacturer("BMW", "Lviv"));
        manufacturerDao.create(new Manufacturer("Mercedes", "Berdyansk"));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(SOME_RANDOM_ID_SECOND);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(SOME_RANDOM_ID_FIRST).get());
    }
}
