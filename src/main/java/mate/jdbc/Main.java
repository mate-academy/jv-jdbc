package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("a");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("b");
        manufacturer1.setCountry("Canada");
        manufacturerDao.create(manufacturer1);

        manufacturerDao.get(3L);

        manufacturer1.setCountry("USA");
        manufacturerDao.update(manufacturer1);

        manufacturerDao.delete(7L);
        manufacturerDao.delete(20L);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
