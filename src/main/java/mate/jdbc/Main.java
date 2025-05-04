package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Zaabar");
        manufacturer.setCountry("Belgium");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(savedManufacturer.getId());
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(savedManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
