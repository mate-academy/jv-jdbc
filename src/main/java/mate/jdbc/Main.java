package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerMercedes = new Manufacturer("Mercedes", "Germany");
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        manufacturerDao.create(manufacturerMercedes);
        manufacturerDao.create(manufacturerAudi);
        manufacturerDao.delete(manufacturerMercedes.getId());
        manufacturerAudi.setCountry("USA");
        manufacturerDao.update(manufacturerAudi);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
