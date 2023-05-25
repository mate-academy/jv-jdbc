package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Czech");
        manufacturer.setName("Skoda");
        System.out.println(manufacturerDao.create(manufacturer));
        manufacturer.setCountry("Italy");
        manufacturer.setName("Ferrari");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println(manufacturerDao.get(6L));

        System.out.println(manufacturerDao.delete(2L));
        System.out.println(manufacturerDao.getAll());
    }
}
