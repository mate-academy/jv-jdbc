package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Ford", "USA");
        System.out.println(manufacturerDao.create(manufacturer));
        System.out.println(manufacturerDao.get((long) 1));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete((long) 3));
        manufacturer.setCountry("DAEWOO");
        manufacturer.setName("Ukraine");
        System.out.println(manufacturerDao.update(manufacturer));
    }
}

