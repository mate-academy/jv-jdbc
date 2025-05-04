package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Jeep", "USA");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        manufacturer.setName("Tesla");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
    }
}
