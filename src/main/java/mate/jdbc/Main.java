package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (
                ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Daewoo Lanos");
        manufacturer.setCountry("South Korea");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(savedManufacturer);
        savedManufacturer.setCountry("North Korea");
        manufacturerDao.update(savedManufacturer);
        System.out.println(manufacturerDao.get(savedManufacturer.getId()).orElse(null));
        System.out.println(manufacturerDao.delete(savedManufacturer.getId()));
        System.out.println(manufacturerDao.get(1L).orElse(null));
        System.out.println(manufacturerDao.get(5L).orElse(null));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
