package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer yamahaPiano = new Manufacturer();
        yamahaPiano.setName("Yamaha");
        yamahaPiano.setCountry("Japan");
        Manufacturer steinway = new Manufacturer();
        steinway.setName("Steinway");
        steinway.setCountry("USA");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer yamahaPiano1 = manufacturerDao.create(yamahaPiano);

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));

        yamahaPiano1.setCountry("Germany");
        yamahaPiano1.setName("Weinbach");

        manufacturerDao.update(yamahaPiano1);

        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
