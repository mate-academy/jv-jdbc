package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer fiat = new Manufacturer("Fiat", "Italy");
        Manufacturer toyota = new Manufacturer("Toyota", "China");
        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");

        manufacturerDao.create(fiat);
        manufacturerDao.create(toyota);
        manufacturerDao.create(mercedes);

        System.out.println(manufacturerDao.get(toyota.getId()));

        toyota.setCountry("Japan");
        manufacturerDao.update(toyota);

        manufacturerDao.delete(mercedes.getId());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
