package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer audi = manufacturerDao.create(
                new Manufacturer("Audi", "Germany"));
        Manufacturer mazda = manufacturerDao.create(
                new Manufacturer("Mazda", "Japan"));
        Manufacturer tesla = manufacturerDao.create(
                new Manufacturer("Tesla", "U.S."));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.delete(2L);
        manufacturerDao.update(
                new Manufacturer(5L, "Reno", "France"));
    }
}
