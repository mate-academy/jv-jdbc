package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        mercedes = manufacturerDao.create(mercedes);
        bmw = manufacturerDao.create(bmw);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(bmw.getId()));
        System.out.println(manufacturerDao.get(mercedes.getId()));
        manufacturerDao.delete(bmw.getId());
        System.out.println(manufacturerDao.getAll());
        mercedes.setCountry("Ukraine");
        manufacturerDao.update(mercedes);
        System.out.println(manufacturerDao.getAll());
    }
}
