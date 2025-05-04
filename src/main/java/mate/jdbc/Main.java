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
        manufacturer.setName("bmw");
        manufacturer.setCountry("germany");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);

        manufacturerDao.get(savedManufacturer.getId());

        manufacturer.setId(10L);
        manufacturer.setName("toyota");
        manufacturer.setCountry("japan");

        manufacturerDao.update(manufacturer);

        manufacturerDao.delete(15L);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
