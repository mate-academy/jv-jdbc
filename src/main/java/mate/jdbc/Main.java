package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer siemens = new Manufacturer();
        siemens.setName("Siemens");
        siemens.setCountry("Germany");

        Manufacturer toshiba = new Manufacturer();
        toshiba.setName("Toshiba");
        toshiba.setCountry("Japan");

        Manufacturer samsung = new Manufacturer();
        samsung.setName("Samsung");
        samsung.setCountry("Korea");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        System.out.println(manufacturerDao.create(siemens));
        System.out.println(manufacturerDao.create(toshiba));
        System.out.println(manufacturerDao.create(samsung));

        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(3L));
        System.out.println(manufacturerDao.get(50L));

        siemens.setName("Siemens-GMBH");
        samsung.setCountry("South Korea");

        System.out.println(manufacturerDao.update(siemens));
        System.out.println(manufacturerDao.update(samsung));

        System.out.println(manufacturerDao.delete(1L));
        System.out.println(manufacturerDao.delete(3L));
        System.out.println(manufacturerDao.delete(50L));

        System.out.println(manufacturerDao.getAll());
    }
}
