package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerA = new Manufacturer(1L, "A", "AA");
        Manufacturer manufacturerB = new Manufacturer(2L, "B", "BB");
        Manufacturer manufacturerC = new Manufacturer(3L, "C", "CC");
        Manufacturer manufacturerD = new Manufacturer(4L, "D", "DD");
        Manufacturer manufacturerE = new Manufacturer(5L, "E", "EE");
        System.out.println(manufacturerDao.create(manufacturerA));
        System.out.println(manufacturerDao.create(manufacturerB));
        System.out.println(manufacturerDao.create(manufacturerC));
        System.out.println(manufacturerDao.create(manufacturerD));
        System.out.println(manufacturerDao.create(manufacturerE));
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerE.setName("EEE");
        manufacturerE.setCountry("EEEEE");
        System.out.println(manufacturerDao.update(manufacturerE));
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(manufacturerE.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
