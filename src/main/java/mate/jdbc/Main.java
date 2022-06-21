package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.deleteAll());
        Manufacturer volkswagen = manufacturerDao
                .create(new Manufacturer("Volkswagen", "Germany"));
        Manufacturer toyota = manufacturerDao.create(new Manufacturer("Toyota", "Japan"));
        System.out.println(volkswagen);
        System.out.println(toyota);
        System.out.println(manufacturerDao.get(volkswagen.getId()));
        System.out.println(manufacturerDao.getAll());
        toyota.setCountry("Antarctica");
        System.out.println(manufacturerDao.update(toyota));
        System.out.println(manufacturerDao.get(toyota.getId()));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(volkswagen.getId()));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(volkswagen.getId()));
        System.out.println(manufacturerDao.delete(toyota.getId()));
        System.out.println(manufacturerDao.getAll());
    }
}
