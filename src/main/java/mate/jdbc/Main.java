package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer porsche = new Manufacturer("Porsche", "Germany");
        Manufacturer tesla = new Manufacturer("Tesla", "USA");

        porsche = manufacturerDao.create(porsche);
        tesla = manufacturerDao.create(tesla);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(tesla.getId()));
        System.out.println(manufacturerDao.get(porsche.getId()));
        manufacturerDao.delete(tesla.getId());
        System.out.println(manufacturerDao.getAll());
        porsche.setCountry("China");
        manufacturerDao.update(porsche);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
