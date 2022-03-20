package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.creat(new Manufacturer(null, "Bob", "USA")));
        manufacturerDao.deleted(1L);
        manufacturerDao.update(new Manufacturer(1L, "Jessica", "USA"));
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.getAll();
    }
}
