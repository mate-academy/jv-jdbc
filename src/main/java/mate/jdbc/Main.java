package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer kia = new Manufacturer("Kia", "South Korea");
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer volkswagen = new Manufacturer("Volkswagen", "Germany");
        manufacturerDao.create(volkswagen);
        manufacturerDao.create(kia);
        manufacturerDao.create(toyota);
        manufacturerDao.create(bmw);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(bmw.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
