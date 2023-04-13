package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerSony = new Manufacturer();
        manufacturerSony.setName("Sony");
        manufacturerSony.setCountry("USA");

        Manufacturer manufacturerNvidia = new Manufacturer();
        manufacturerNvidia.setName("Nvidia");
        manufacturerNvidia.setCountry("China");

        manufacturerDao.create(manufacturerSony);
        manufacturerDao.create(manufacturerNvidia);
        manufacturerDao.delete(11L);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(1L));
    }
}
