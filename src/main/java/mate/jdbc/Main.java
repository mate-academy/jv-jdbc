package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(null, "ZAZ", "Ukraine");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("FORD");
        manufacturer.setCountry("USA");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
