package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerMercedes = new Manufacturer();
        manufacturerMercedes.setName("mercedes");
        manufacturerMercedes.setCountry("Germany");
        manufacturerDao.create(manufacturerMercedes);
        Manufacturer manufacturerAudi = new Manufacturer();
        manufacturerAudi.setName("audi");
        manufacturerAudi.setCountry("Germany");
        manufacturerDao.create(manufacturerAudi);
        System.out.println(manufacturerDao.get(1L));
        manufacturerAudi.setCountry("Ukraine");
        manufacturerDao.update(manufacturerAudi);
        manufacturerDao.delete(9L);
        System.out.println(manufacturerDao.getAll());

    }
}
