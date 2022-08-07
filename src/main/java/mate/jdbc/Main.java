package mate.jdbc;

import java.util.List;
import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.lib.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

        System.out.println(manufacturerDao.get(2L).get());
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();

        Manufacturer manufacturerZaz = new Manufacturer("ZAZ", "Ukraine");
        Manufacturer savedManufacturerZaz = manufacturerDao.create(manufacturerZaz);

        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();

        manufacturerDao.delete(savedManufacturerZaz.getId());

        manufacturerDao.getAll().forEach(System.out::println);

        //Injector injector = Injector.getInstance("mate.jdbc");
        //ManufacturerDao manufacturerDao = (ManufacturerDao) injector
        // .getInstance(ManufacturerDao.class);
    }
}
