package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();

        System.out.println("------create------");
        manufacturer.setName("Mitsubishi ");
        manufacturer.setCountry("Japan");
        System.out.println(manufacturerDao.create(manufacturer) + System.lineSeparator());

        System.out.println("------get------");
        System.out.println(manufacturerDao.get(manufacturer.getId()) + System.lineSeparator());

        System.out.println("------update------");
        manufacturer.setName("Mercedes ");
        manufacturer.setCountry("Germany");
        System.out.println(manufacturerDao.update(manufacturer) + System.lineSeparator());

        System.out.println("------delete-----");
        System.out.println(manufacturerDao.delete(manufacturer.getId()) + System.lineSeparator());

        System.out.println("------getAll------");
        System.out.println(manufacturerDao.getAll() + System.lineSeparator());
    }
}
