package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.getAll());
        Manufacturer test = new Manufacturer(1L, "ferrari", "italy");
        manufacturerDao.update(test);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(2L));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.getAll());
    }
}
