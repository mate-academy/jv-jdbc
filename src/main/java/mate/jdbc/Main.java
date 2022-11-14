package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(null, "Test name", "Test country");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("BMW");
        manufacturer.setCountry("GERMAN");
        manufacturer = manufacturerDao.create(manufacturer);
        System.out.println("ADDED:"
                + manufacturerDao.get(manufacturer.getId()).orElse(new Manufacturer()));
        manufacturer.setCountry("Japan");
        manufacturer = manufacturerDao.update(manufacturer);
        System.out.println("UPDATED: "
                + manufacturerDao.get(manufacturer.getId()).orElse(new Manufacturer()));
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        System.out.println("DELETED: "
                + manufacturerDao.get(manufacturer.getId()));
    }
}
