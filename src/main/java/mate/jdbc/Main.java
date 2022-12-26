package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Ford");
        manufacturer.setCountry("USA");
        manufacturerDao.create(manufacturer);
        manufacturer = manufacturerDao.get(2L).get();
        manufacturer.setName("Jeneral Motors");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(1L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
