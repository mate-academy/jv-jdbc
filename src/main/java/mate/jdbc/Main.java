package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufactureDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = manufactureDao.create(new Manufacturer("Honda", "Kyiv"));
        manufacturer = manufactureDao.create(new Manufacturer("BMW", "Lviv"));
        manufacturer = manufactureDao.create(new Manufacturer("Mercedes", "Berdyansk"));
        System.out.println(manufactureDao.getAll());
        manufactureDao.delete(2L);
        System.out.println(manufactureDao.getAll());
        System.out.println(manufactureDao.get(1L).get());
    }
}
