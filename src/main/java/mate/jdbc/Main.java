package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args)  {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer("Lada", "Russsia"));
        manufacturerDao.create(new Manufacturer("Zaz", "Ukraine"));
        Manufacturer testManufacturer = new Manufacturer("Pegout", "France");
        manufacturerDao.create(testManufacturer);

        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.getAll());

        testManufacturer.setName("Pegout_208");
        manufacturerDao.update(testManufacturer);
        System.out.println(manufacturerDao.getAll());

        manufacturerDao.delete(2L);
        System.out.println(manufacturerDao.getAll());

    }
}
