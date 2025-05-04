package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        Manufacturer testManufacturer = new Manufacturer();
        testManufacturer.setName("test");
        testManufacturer.setCountry("Ukraine");

        System.out.println(manufacturerDao.create(testManufacturer));

        testManufacturer.setCountry("USA");
        System.out.println(manufacturerDao.update(testManufacturer));

        System.out.println(manufacturerDao.get(testManufacturer.getId()));

        System.out.println(manufacturerDao.delete(testManufacturer.getId()));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
