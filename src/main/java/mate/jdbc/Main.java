package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("manufacturerTest", "Ukraine");
        System.out.println(manufacturerDao.create(manufacturer));
        System.out.println("get " + System.lineSeparator() + manufacturerDao.get(17L));
        System.out.println("update: " + System.lineSeparator()
                + manufacturerDao.update(new Manufacturer(17L,
                "manufacturer", "USA")));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(7L);
        System.out.println(manufacturerDao.delete(-10L));
    }
}
