package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("---------------------Test method getAll---------------------");
        System.out.println(manufacturerDao.getAll());
        System.out.println("---------------------Test method get - by id---------------------");
        manufacturerDao.get(1L).ifPresent(System.out::println);
        System.out.println(manufacturerDao.get(10L).orElse(null));
        System.out.println(
                "---------------------Test method create - create new record in DB"
                        + "---------------------");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("bmw");
        manufacturer.setCountry("Germany");
        System.out.println(manufacturer);
        System.out.println("----------------------------------------");
        Manufacturer manufacturerWithId = manufacturerDao.create(manufacturer);
        System.out.println(manufacturerWithId);
        System.out.println(
                "---------------------Test method delete - delete record from DB"
                        + "---------------------");
        System.out.println(manufacturerDao.getAll());
        System.out.println("----------------------------------------");
        boolean isDeleted = manufacturerDao.delete(3L);
        System.out.println(isDeleted);
        System.out.println("----------------------------------------");
        System.out.println(manufacturerDao.getAll());
        System.out.println(
                "---------------------Test method update - update data in DB--------------------");
        System.out.println(manufacturerDao.getAll());
        System.out.println("----------------------------------------");
        Manufacturer manufacturerSecond = new Manufacturer();
        manufacturerSecond.setId(2L);
        manufacturerSecond.setName("bmw");
        manufacturerSecond.setCountry("Germany-Poland");
        System.out.println(manufacturerDao.update(manufacturerSecond));
    }
}
