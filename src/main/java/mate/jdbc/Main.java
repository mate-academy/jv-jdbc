package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        audi = manufacturerDao.create(audi);
        toyota = manufacturerDao.create(toyota);
        ford = manufacturerDao.create(ford);
        System.out.println(manufacturerDao.get(toyota.getId()));
        manufacturerDao.delete(audi.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
