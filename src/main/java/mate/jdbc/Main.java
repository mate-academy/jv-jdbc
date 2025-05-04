package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer lada = new Manufacturer("Lada", "Soviet Union");
        Manufacturer zaz = new Manufacturer("ZAZ", "Ukraine");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        manufacturerDao.create(toyota);
        manufacturerDao.create(lada);
        manufacturerDao.create(zaz);
        manufacturerDao.create(ford);
        lada.setCountry("Russia");
        manufacturerDao.update(lada);
        manufacturerDao.delete(ford.getId());
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(zaz.getId()));
    }
}
