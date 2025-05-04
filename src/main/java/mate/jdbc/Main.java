package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");

        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japanese");

        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");

        Manufacturer tesla = new Manufacturer();
        tesla.setName("Tesla");
        tesla.setCountry("USA");

        Manufacturer bmwNew = manufacturerDao.create(bmw);
        Manufacturer toyotaNew = manufacturerDao.create(toyota);
        Manufacturer mercedesNew = manufacturerDao.create(mercedes);
        Manufacturer teslaNew = manufacturerDao.create(tesla);

        System.out.println(manufacturerDao.update(mercedesNew));
        System.out.println(manufacturerDao.update(teslaNew));

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}

