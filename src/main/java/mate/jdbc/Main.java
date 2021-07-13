package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer("Renault","France"));
        manufacturerDao.create(new Manufacturer("Christian Dior","France"));
        manufacturerDao.create(new Manufacturer("United Technologies","United States"));
        manufacturerDao.create(new Manufacturer("Lockheed Martin","United States"));
        manufacturerDao.create(new Manufacturer("Hewlett-Packard","United States"));
        System.out.println(manufacturerDao.get(15L));
        Manufacturer updateManufacturer = new Manufacturer("Unilever","United Kingdom");
        updateManufacturer.setId(15L);
        manufacturerDao.update(updateManufacturer);
        System.out.println(manufacturerDao.get(15L));
        manufacturerDao.delete(14L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
