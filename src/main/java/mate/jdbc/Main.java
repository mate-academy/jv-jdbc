package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer volkswagen = manufacturerDao.create(
                new Manufacturer("Volkswagen", "Germany"));
        Manufacturer hyundai = manufacturerDao.create(
                new Manufacturer("Hyundai", "South Korea"));
        Manufacturer cherry = manufacturerDao.create(
                new Manufacturer("Cherry", "China"));
        System.out.println(manufacturerDao.get(2L).get());
        System.out.println(manufacturerDao.get(3L).get());
        Manufacturer cherryToHaval = manufacturerDao.update(
                new Manufacturer(3L, "Haval", "China"));
        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.clear();
    }
}
