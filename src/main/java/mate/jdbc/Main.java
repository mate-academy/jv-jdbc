package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerToyota = new Manufacturer("Toyota", "Japan");
        Manufacturer manufacturerMercedes = new Manufacturer("Mercedes", "Germany");
        manufacturerDao.create(manufacturerToyota);
        manufacturerDao.create(manufacturerMercedes);
        manufacturerDao.delete(manufacturerMercedes.getId());
        manufacturerDao.update(manufacturerToyota);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
