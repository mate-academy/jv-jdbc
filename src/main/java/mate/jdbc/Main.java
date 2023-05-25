package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer audi = new Manufacturer("Audi", "German");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");

        manufacturerDao.create(audi);
        manufacturerDao.create(ford);
        manufacturerDao.create(toyota);

        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        System.out.println(manufacturerList);
        System.out.println(manufacturerDao.get(audi.getId()));
        manufacturerDao.update(toyota);
        manufacturerDao.delete(ford.getId());
        System.out.println(manufacturerList);
    }
}
