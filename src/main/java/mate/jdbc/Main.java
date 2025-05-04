package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer tesla = new Manufacturer("Tesla", "United States");
        Manufacturer samsung = new Manufacturer("Samsung", "England");
        manufacturerDao.create(tesla);
        manufacturerDao.create(samsung);
        System.out.println("Initial DB: ");
        manufacturerDao.getAll().forEach(System.out::println);
        samsung.setCountry("Switzerland");
        manufacturerDao.update(samsung);
        System.out.println("DB after updating: ");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Manufacturer by index 1: ");
        System.out.println(manufacturerDao.get(1L).get());
        manufacturerDao.delete(1L);
        System.out.println("DB after deleting: ");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Manufacturer by index 1 after deleting: ");
        System.out.println(manufacturerDao.get(1L));
    }
}
