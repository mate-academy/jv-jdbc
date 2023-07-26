package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerZaz = new Manufacturer("ZAZ","Ukraine");
        Manufacturer manufacturerRover = new Manufacturer("Rover","UK");
        manufacturerDao.create(manufacturerZaz);
        manufacturerDao.get(manufacturerZaz.getId());
        manufacturerDao.create(manufacturerRover);
        manufacturerDao.update(manufacturerZaz);
        manufacturerDao.delete(manufacturerZaz.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
