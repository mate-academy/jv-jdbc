package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bars = manufacturerDao
                .create(new Manufacturer("Bars", "Ukraine"));
        Manufacturer subaru = manufacturerDao
                .create(new Manufacturer("Subaru", "Japan"));
        System.out.println(manufacturerDao.get(1L));
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
        Manufacturer mazda = manufacturerDao
                .create(new Manufacturer("Mazda", "Japan"));
        System.out.println(manufacturerDao.delete(3L));
        System.out.println(manufacturerDao.update(mazda));
    }
}
