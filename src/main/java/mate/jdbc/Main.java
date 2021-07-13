package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.get(3L));
        System.out.println(manufacturerDao.delete(9L));
        System.out.println(manufacturerDao.create(new Manufacturer("Audi", "German")));
        System.out.println(manufacturerDao.update(new Manufacturer(7L,"vushuvanka", "Ukriane")));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
