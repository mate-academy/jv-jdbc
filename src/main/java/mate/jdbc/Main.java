package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer testManufacturer1 = new Manufacturer();
        testManufacturer1.setName("Volkswagen");
        testManufacturer1.setCountry("Germany");
        Manufacturer testManufacturer2 = new Manufacturer();
        testManufacturer2.setName("Mercedes-Benz");
        testManufacturer2.setCountry("Germany");
        System.out.println("Table before all operations:");
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.create(testManufacturer1);
        manufacturerDao.create(testManufacturer2);
        System.out.println("Table after inserting manufacturers:");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Updating manufacturer2 name to Geely and country to China:");
        testManufacturer2.setName("Geely");
        testManufacturer2.setCountry("China");
        manufacturerDao.update(testManufacturer2);
        System.out.println("Getting manufacturer2 by id:");
        System.out.println(manufacturerDao.get(testManufacturer2.getId()));
        System.out.println("Deleting manufacturer1 by id:");
        System.out.println(manufacturerDao.delete(testManufacturer1.getId()));
        System.out.println("Table after all operations:");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
