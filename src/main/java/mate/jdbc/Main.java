package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturer1 = manufacturerDao.create(new Manufacturer("name1", "country1"));
        System.out.println("Add to db manufacturer:" + manufacturer1);
        Manufacturer manufacturer2 = manufacturerDao.create(new Manufacturer("name2", "country2"));
        System.out.println("Add to db manufacturer:" + manufacturer2);
        Manufacturer manufacturer3 = manufacturerDao.create(new Manufacturer("name3", "country3"));
        System.out.println("Add to db manufacturer:" + manufacturer3);
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        Optional<Manufacturer> getFromDbManufacturer = manufacturerDao.get(1L);
        System.out.println("Result of get(id = 1): " + getFromDbManufacturer.get());
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        Manufacturer updatedManufacturer2 = new Manufacturer(2L, "name", "country");
        System.out.println("getAll() result after update manufacturer with id = 2:");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        boolean isDeleted = manufacturerDao.delete(2L);
        System.out.println("Manufacturer with id = " + 2L
                + " is deleted: " + isDeleted);
        System.out.println("getAll() result after delete manufacturer with id = 2:");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
