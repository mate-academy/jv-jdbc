package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(
                ManufacturerDao.class);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(5L);
        manufacturer.setName("Honda");
        manufacturer.setCountry("Thailand");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(6L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
