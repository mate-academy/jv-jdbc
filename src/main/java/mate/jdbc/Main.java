package mate.jdbc;

import mate.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.model.Manufacturer;

public class Main {
    private static Injector instance = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) instance.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Stasik", "USA");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Stas");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(17L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
